package com.etnetera.hr.controller;

import com.etnetera.hr.config.Consts;
import com.etnetera.hr.config.CustomUriBuilder;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.Version;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.repository.VersionRepository;
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

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VersionControllerTest {

    private Version version;
    private JavaScriptFramework javaScriptFramework;
    private Date depricationDate;

    @Autowired
    private VersionRepository versionRepository;

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

        GregorianCalendar date = new GregorianCalendar(Consts.YEAR, Consts.MONTH, Consts.DAY);
        depricationDate = new java.sql.Date(date.getTimeInMillis());

        version = new Version(Consts.VERSION_ID, Consts.VERSION_NAME, Consts.HYPE_LEVEL, depricationDate, javaScriptFramework);
        versionRepository.save(version);
    }

    @Test
    public void versions() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/version/all",
                HttpMethod.GET, entity, String.class);

        assertThat(response.getBody()).isEqualTo(Consts.VERSION);
    }

    @Test
    public void getVersion() {
        Version v = restTemplate.getForObject(
                getRootUrl() + "/version/1", Version.class);

        assertThat(v.getVersion()).isEqualTo(Consts.VERSION_NAME);
    }

    @Test
    @DirtiesContext
    public void addVersion() {
        Version v = new Version(
                "2.0",
                Consts.HYPE_LEVEL,
                depricationDate,
                javaScriptFramework
        );

        ResponseEntity<Version> postResponse =
                restTemplate.postForEntity(getRootUrl() + "/version/add", v, Version.class);

        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    @DirtiesContext
    public void updateVersion() {
        Optional<Version> optional = versionRepository.findById(Consts.VERSION_ID);
        Version v = optional.get();
        v.setVersion("2.0");

        restTemplate.put(getRootUrl() + "/version/edit", v);
        Version updated = restTemplate.getForObject(getRootUrl() + "/version/" + Consts.VERSION_ID, Version.class);

        assertEquals("2.0", updated.getVersion());
    }

    @Test
    @DirtiesContext
    public void deleteVersion() {
        restTemplate.delete(getRootUrl() + "/version/" + Consts.VERSION_ID);

        try {
            restTemplate.getForObject(getRootUrl() + "/version/" + Consts.VERSION_ID, Version.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void getVersionsByVersionName() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                customUriBuilder.getURI(getRootUrl(), "/version/v", "v", Consts.VERSION_NAME),
                HttpMethod.GET,
                entity,
                String.class
        );

        assertThat(response.getBody()).isEqualTo(Consts.VERSION);
    }

    @Test
    public void getVersionsByHypeLevel() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                customUriBuilder.getURI(getRootUrl(), "/version/hypeLevel", "hypeLevel", Consts.HYPE_LEVEL),
                HttpMethod.GET,
                entity,
                String.class
        );

        assertThat(response.getBody()).isEqualTo(Consts.VERSION);
    }

    @Test
    public void getVersionsByDeprecationDate() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                customUriBuilder.getURI(getRootUrl(), "/version/deprecationDate", "deprecationDate", depricationDate),
                HttpMethod.GET,
                entity,
                String.class
        );

        assertThat(response.getBody()).isEqualTo(Consts.VERSION);
    }

    @Test
    public void getVersionsByJavaScriptFramework_Id() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                customUriBuilder.getURI(getRootUrl(), "/version/javaScriptFramework/id", "id", Consts.ID),
                HttpMethod.GET,
                entity,
                String.class
        );

        assertThat(response.getBody()).isEqualTo(Consts.VERSION);
    }

    @Test
    public void getVersionsByJavaScriptFramework_Name() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                customUriBuilder.getURI(getRootUrl(), "/version/javaScriptFramework/name", "name", Consts.NAME),
                HttpMethod.GET,
                entity,
                String.class
        );

        assertThat(response.getBody()).isEqualTo(Consts.VERSION);
    }
}