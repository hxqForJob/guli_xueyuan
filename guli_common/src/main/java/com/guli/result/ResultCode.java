package com.guli.result;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("结果码")
public enum  ResultCode {

    OK(20000),ERROR(20001);

    private int code;

    private  ResultCode(int code)
    {
        this.code=code;
    }
    public  int getCode()
    {
        return  code;
    }


}
