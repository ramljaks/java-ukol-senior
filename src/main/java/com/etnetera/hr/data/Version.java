package com.etnetera.hr.data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

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
    @JoinColumn(name = "id", nullable = false)
    private JavaScriptFramework javaScriptFramework;

    public Version() {
    }

    public Version(String version, String hypeLevel, Date deprecationDate, JavaScriptFramework javaScriptFramework) {
        this.version = version;
        this.hypeLevel = hypeLevel;
        this.deprecationDate = deprecationDate;
        this.javaScriptFramework = javaScriptFramework;
    }

    public Version(Long versionId, String version, String hypeLevel, Date deprecationDate, JavaScriptFramework javaScriptFramework) {
        this.versionId = versionId;
        this.version = version;
        this.hypeLevel = hypeLevel;
        this.deprecationDate = deprecationDate;
        this.javaScriptFramework = javaScriptFramework;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Version)) return false;
        Version version1 = (Version) o;
        return versionId.equals(version1.versionId) &&
                version.equals(version1.version) &&
                hypeLevel.equals(version1.hypeLevel) &&
                deprecationDate.equals(version1.deprecationDate) &&
                javaScriptFramework.equals(version1.javaScriptFramework);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionId, version, hypeLevel, deprecationDate, javaScriptFramework);
    }
}