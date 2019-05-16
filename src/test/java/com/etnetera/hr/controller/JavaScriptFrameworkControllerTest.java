package com.etnetera.hr.controller;

import com.etnetera.hr.config.Consts;
import com.etnetera.hr.config.CustomUriBuilder;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JavaScriptFrameworkControllerTest {

    private JavaScriptFramework javaScriptFramework;

    @Autowired
    private JavaScriptFrameworkRepository javaScriptFrameworkRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomUriBuilder customUriBuilder;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Before
    public void beforeTest() {
        javaScriptFramework = new JavaScriptFramework(Consts.ID, Consts.NAME);
        javaScriptFrameworkRepository.save(javaScriptFramework);
    }

    @Test
    public void testFrameworks() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/framework/all",
                HttpMethod.GET, entity, String.class);

        assertThat(response.getBody()).isEqualTo(Consts.JAVA_SCRIPT_FRAMEWORK);
    }

    @Test
    public void testGetFramework() {
        JavaScriptFramework framework = restTemplate.getForObject(
                getRootUrl() + "/framework/1", JavaScriptFramework.class);

        assertThat(framework.getName()).isEqualTo(Consts.NAME);
    }

    @Test
    public void getFrameworkByName() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                customUriBuilder.getURI(getRootUrl(), "framework/name", "name", Consts.NAME),
                HttpMethod.GET,
                entity,
                String.class);

        assertThat(response.getBody()).isEqualTo(Consts.JAVA_SCRIPT_FRAMEWORK);
    }

    @Test
    @DirtiesContext
    public void addFramework() {
        JavaScriptFramework framework = new JavaScriptFramework("test_create");
        ResponseEntity<JavaScriptFramework> postResponse =
                restTemplate.postForEntity(getRootUrl() + "/framework/add", framework, JavaScriptFramework.class);

        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    @DirtiesContext
    public void updateFramework() {
        Optional<JavaScriptFramework> optional = javaScriptFrameworkRepository.findById(Consts.ID);
        JavaScriptFramework framework = optional.get();
        framework.setName("test_update");

        restTemplate.put(getRootUrl() + "/framework/edit", framework);
        JavaScriptFramework updated = restTemplate.getForObject(getRootUrl() + "/framework/" + Consts.ID, JavaScriptFramework.class);

        assertEquals("test_update", updated.getName());
    }

    @Test
    @DirtiesContext
    public void deleteFramework() {
        restTemplate.delete(getRootUrl() + "/framework/" + Consts.ID);

        try {
            restTemplate.getForObject(getRootUrl() + "/framework/" + Consts.ID, JavaScriptFramework.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}