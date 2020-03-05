package com.lightspace.manage_cms_client.dao;

import com.lightspace.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}

