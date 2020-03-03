package com.lightspace.manage_cms.dao;

import com.lightspace.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
