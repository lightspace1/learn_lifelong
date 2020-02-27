package com.lightspace.framework.domain.cms.response;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    private Map mapValue;
    private String value;

}
