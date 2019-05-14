package com.etnetera.hr;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.repository.VersionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaScriptFrameworkTests {

    @Autowired
    private JavaScriptFrameworkRepository frameworkRepository;

    @Autowired
    private VersionRepository versionRepository;

    @Test
    public void createVersion(){
        JavaScriptFramework javaScriptFramework = new JavaScriptFramework(
                "Framework test"
        );

        JavaScriptFramework resultFramework = frameworkRepository.save(javaScriptFramework);
    }
}
