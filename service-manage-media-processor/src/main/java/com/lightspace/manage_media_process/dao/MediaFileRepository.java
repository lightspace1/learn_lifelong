package com.lightspace.manage_media_process.dao;

import com.lightspace.framework.domain.media.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaFileRepository extends MongoRepository<MediaFile,String> {
}
