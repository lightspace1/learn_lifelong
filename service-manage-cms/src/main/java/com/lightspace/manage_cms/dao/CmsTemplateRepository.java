package com.lightspace.manage_cms.dao;

import com.lightspace.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
}
