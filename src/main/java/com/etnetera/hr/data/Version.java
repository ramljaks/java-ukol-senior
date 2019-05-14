package com.etnetera.hr.data;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long versionId;

    @Column(nullable = false, length = 30)
    private String version;

    @Column(nullable = false, length = 30)
    private String hypeLevel;

    @Column(nullable = false)
    private Date deprecationDate;

    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private JavaScriptFramework javaScriptFramework;

    public Version() {
    }

    public Version(JavaScriptFramework javaScriptFramework, String version, String hypeLevel, Date deprecationDate) {
        this.javaScriptFramework = javaScriptFramework;
        this.version = version;
        this.hypeLevel = hypeLevel;
        this.deprecationDate = deprecationDate;
    }

    public JavaScriptFramework getJavaScriptFramework() {
        return javaScriptFramework;
    }

    public void setJavaScriptFramework(JavaScriptFramework javaScriptFramework) {
        this.javaScriptFramework = javaScriptFramework;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHypeLevel() {
        return hypeLevel;
    }

    public void setHypeLevel(String hypeLevel) {
        this.hypeLevel = hypeLevel;
    }

    public Date getDeprecationDate() {
        return deprecationDate;
    }

    public void setDeprecationDate(Date deprecationDate) {
        this.deprecationDate = deprecationDate;
    }

    @Override
    public String toString() {
        return "Version{" +
                "versionId=" + versionId +
                ", version='" + version + '\'' +
                ", hypeLevel='" + hypeLevel + '\'' +
                ", deprecationDate=" + deprecationDate +
                ", javaScriptFramework=" + javaScriptFramework +
                '}';
    }
}