package com.etnetera.hr.controller;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 */
@RestController
public class JavaScriptFrameworkController {

    private Logger LOG = LoggerFactory.getLogger(JavaScriptFramework.class);

    private final JavaScriptFrameworkRepository repository;

    @Autowired
    public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/frameworks")
    public Iterable<JavaScriptFramework> frameworks() {
        return repository.findAll();
    }

    @GetMapping("/framework/{id}")
    public ResponseEntity<JavaScriptFramework> getFramework(@PathVariable Long id) {
        Optional<JavaScriptFramework> framework = repository.findById(id);
        return framework.map(res -> ResponseEntity.ok().body(res))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<JavaScriptFramework> addFramework(@RequestBody JavaScriptFramework javaScriptFramework) {
        LOG.info("Request to create JavaScriptFramwork: {}", javaScriptFramework);
        JavaScriptFramework result = repository.save(javaScriptFramework);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/edit")
    public ResponseEntity<JavaScriptFramework> updateFramework(@Valid @RequestBody JavaScriptFramework javaScriptFramework) {
        LOG.info("Request to update JavaScriptFramwork: {}", javaScriptFramework);
        JavaScriptFramework result = repository.save(javaScriptFramework);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/framework/{id}")
    public ResponseEntity<JavaScriptFramework> deleteFramework(@PathVariable Long id) {
        LOG.info("Request to delete JavaScriptFramwork with id: {}", id);
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
