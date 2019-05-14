package com.etnetera.hr.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 *
 * @author Etnetera
 */
@Entity
public class JavaScriptFramework{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "javaScriptFramework")
    Collection<Version> versions;

    public JavaScriptFramework() {
    }

    public JavaScriptFramework(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Version> getVersions() {
        return versions;
    }

    public void setVersions(Collection<Version> versions) {
        this.versions = versions;
    }

    @Override
    public String toString() {
        return "JavaScriptFramework{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", versions=" + versions +
                '}';
    }
}
