package com.guli.teacher.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel("自定义异常")
@Data
public class TeacherException extends   RuntimeException {

    @ApiModelProperty("状态码")
    private int code;
    @ApiModelProperty("异常信息")
    private String message;

    @ApiModelProperty("异常类型")
    private String exType;

    public TeacherException(int Code,String msg){
        this.code=Code;
        this.message=msg;
    }

    public TeacherException(int code, String message, String exType) {
        this.code = code;
        this.message = message;
        this.exType = exType;
    }

    public TeacherException() {
    }
}
