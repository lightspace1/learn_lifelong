package com.lightspace.manage_cms.service;

import com.lightspace.framework.domain.system.SysDictionary;
import com.lightspace.manage_cms.dao.SysDictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysdictionaryService {
    @Autowired
    SysDictionaryDao sysDictionaryDao;
    public SysDictionary findDictionaryByType(String type){
        return sysDictionaryDao.findBydType(type);
    }
}
