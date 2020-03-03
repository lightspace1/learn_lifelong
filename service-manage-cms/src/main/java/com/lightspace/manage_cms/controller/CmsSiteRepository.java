package com.lightspace.manage_cms.controller;

import com.lightspace.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
