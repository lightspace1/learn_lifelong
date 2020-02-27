package com.lightspace.framework.domain.course.response;

import com.google.common.collect.ImmutableMap;
import com.lightspace.framework.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@ToString
public enum CourseCode implements ResultCode {
    COURSE_DENIED_DELETE(false,31001,"\n" +
            "Failed to delete the course, only the course of the institution is allowed to be deleted!"),
    COURSE_PUBLISH_PERVIEWISNULL(false,31002,"Course preview is null!"),
    COURSE_PUBLISH_CDETAILERROR(false,31003,"Course details page create error!"),
    COURSE_PUBLISH_COURSEIDISNULL(false,31004,"\n" +
            "Course Id is null!"),
    COURSE_PUBLISH_VIEWERROR(false,31005,"\n" +
            "Error: publishing course view!"),
    COURSE_PUBLISH_CREATECOURSEPUB_ERROR(false,31006,"Error: creating course index information！"),
    COURSE_MEDIA_URLISNULL(false,31101,"The access address of the selected media asset file is null!"),
    
    COURSE_MEDIA_NAMEISNULL(false,31102,"The selected media asset file name is empty!"),
    COURSE_GET_NOTEXISTS(false,31103,"\n" +
            "No course information found!"),
    COURSE_MEDIA_TEACHPLAN_GRADEERROR(false,31104,"Only the third level lesson plan associated videos are allowed!");



    @ApiModelProperty(value = "Weather operation success", example = "true", required = true)
    boolean success;


    @ApiModelProperty(value = "Operation code", example = "22001", required = true)
    int code;
    //提示信息
    @ApiModelProperty(value = "Operation message", example = "your operation is too fast!", required = true)
    String message;
    private CourseCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, CourseCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, CourseCode> builder = ImmutableMap.builder();
        for (CourseCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
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
