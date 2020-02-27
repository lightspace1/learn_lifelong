package com.lightspace.framework.domain.cms.response;

import com.lightspace.framework.domain.cms.CmsPage;
import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.framework.model.response.ResultCode;
import lombok.Data;

@Data
public class CmsPageResult extends ResponseResult {
    CmsPage cmsPage;
    public CmsPageResult(ResultCode resultCode, CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
