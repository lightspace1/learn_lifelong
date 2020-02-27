package com.lightspace.framework.domain.cms.response;

import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CmsPostPageResult extends ResponseResult {

    String pageUrl;
    public CmsPostPageResult(ResultCode resultCode, String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}
