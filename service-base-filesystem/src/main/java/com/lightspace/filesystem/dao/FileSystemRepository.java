package com.lightspace.filesystem.dao;

import com.lightspace.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSystemRepository extends MongoRepository<FileSystem,String> {
}

