package com.lightspace.api.cms;

import com.lightspace.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Data dictionary interface",description =
        "Provide data dictionary interface management and query functions")
public interface SysDictionaryControllerApi {
    //数据字典
    @ApiOperation(value=
            "Data dictionary query interface")
    public SysDictionary getByType(String type);
}