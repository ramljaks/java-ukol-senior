package com.etnetera.hr.repository;

import com.etnetera.hr.data.Version;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;

public interface VersionRepository extends CrudRepository<Version, Long> {

    Iterable<Version> findAllByVersion(String version);

    Iterable<Version> findAllByHypeLevel(String hypeLevel);

    Iterable<Version> findAllByDeprecationDate(Date date);

    Iterable<Version> findAllByJavaScriptFramework_Id(Long id);

    Iterable<Version> findAllByJavaScriptFramework_Name(String name);
}
