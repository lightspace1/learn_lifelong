package com.lightspace.manage_cms.dao;

import com.lightspace.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDictionaryDao extends MongoRepository<SysDictionary,String> {
    SysDictionary findBydType(String dType);
}
