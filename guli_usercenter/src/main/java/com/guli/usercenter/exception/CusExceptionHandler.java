package com.guli.usercenter.exception;

import com.guli.result.Result;
import com.guli.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class CusExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        //写入日志
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message("出错了,请查看日志").type(e.getMessage());
    }

    /**
     * 自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(CusException.class)
    @ResponseBody
    public Result error(CusException e){
        e.printStackTrace();
        //写入日志
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(e.getCode()).message(e.getMessage()).type(e.getExType());
    }

}
