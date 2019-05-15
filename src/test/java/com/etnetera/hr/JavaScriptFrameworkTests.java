package com.etnetera.hr;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;


/**
 * Class used for Spring Boot/MVC based tests.
 *
 * @author Etnetera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaScriptFrameworkTests {
    @Autowired
    private JavaScriptFrameworkRepository repository;

    private static final JavaScriptFramework TEST_FRAMEWORK = new JavaScriptFramework("test");

    @Test
    public void frameworks() {
        repository.save(TEST_FRAMEWORK);
        Iterable<JavaScriptFramework> result = repository.findAll();
        Assert.assertEquals(parseLong("1"), result.spliterator().getExactSizeIfKnown());
    }

    @Test
    public void getFramework() {
        JavaScriptFramework create = repository.save(TEST_FRAMEWORK);

        Optional<JavaScriptFramework> optional = repository.findById(create.getId());
        JavaScriptFramework result = optional.get();

        Assert.assertEquals(TEST_FRAMEWORK, result);
    }

    @Test
    public void getFrameworkByName() {
        repository.save(TEST_FRAMEWORK);

        Iterable<JavaScriptFramework> iterable = repository.findAllByName(TEST_FRAMEWORK.getName());
        List<JavaScriptFramework> frameworks = new ArrayList<>();

        iterable.forEach(frameworks::add);

        Assert.assertEquals(TEST_FRAMEWORK.getName(), frameworks.get(0).getName());
    }

    @Test
    public void addFramework() {
        JavaScriptFramework result = repository.save(TEST_FRAMEWORK);

        Assert.assertTrue("Test framework should be created.", !result.equals(null));
    }

    @Test
    public void updateFramework() {
        JavaScriptFramework test = repository.save(TEST_FRAMEWORK);
        Optional<JavaScriptFramework> optional = repository.findById(test.getId());

        JavaScriptFramework javaScriptFramework = optional.get();

        Assert.assertEquals("test", javaScriptFramework.getName());

        javaScriptFramework.setName("update");
        JavaScriptFramework update = repository.save(javaScriptFramework);

        Assert.assertTrue("The id of both frameworks should be the same, but name should be update.",
                javaScriptFramework.getId().equals(update.getId()) &&
                        javaScriptFramework.getName().equals("update"));
    }

    @Test
    public void deleteFramework() {
        JavaScriptFramework test = repository.save(TEST_FRAMEWORK);
        Optional<JavaScriptFramework> optional = repository.findById(test.getId());
        JavaScriptFramework javaScriptFramework = optional.get();

        repository.deleteById(javaScriptFramework.getId());

        Assert.assertTrue("No framework should be found",
                repository.findAll().spliterator().getExactSizeIfKnown() == 0);
    }
}
