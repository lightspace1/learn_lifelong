package com.lightspace.framework.domain.cms.response;

import com.lightspace.framework.model.response.ResultCode;
import lombok.ToString;

@ToString
public enum CmsCode implements ResultCode {
    CMS_ADDPAGE_EXISTSNAME(false,24001,"\n" + "Page already exists!"),
    CMS_GENERATEHTML_DATAURLISNULL(false,24002,"\n" + "Can't find url from page information!"),
    CMS_GENERATEHTML_DATAISNULL(false,24003,"Can't find data from url!"),
    CMS_GENERATEHTML_TEMPLATEISNULL(false,24004,"Page template is empty!"),
    CMS_GENERATEHTML_HTMLISNULL(false,24005,"Generated static html is empty!"),
    CMS_GENERATEHTML_SAVEHTMLERROR(false,24005,"\n" + "Saving static html fail!"),
    CMS_PAGE_NOTEXISTS(false,24006,"\n" + "Page does not exist!"),
    CMS_COURSE_PERVIEWISNULL(false,24007,"Preview page is empty!");
    boolean success;
    int code;
    String message;
    private CmsCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
