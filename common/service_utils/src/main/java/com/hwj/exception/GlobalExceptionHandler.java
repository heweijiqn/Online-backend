package com.hwj.exception;


import com.hwj.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice  //aop
public class GlobalExceptionHandler {

    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        System.out.println("全局.....");
        e.printStackTrace();
        return Result.fail(null).message("*****执行全局异常处理*****");
    }

    //特定异常处理ArithmeticException
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        System.out.println("特定.....");
        e.printStackTrace();
        return Result.fail(null).message("*****执行ArithmeticException异常处理*****");
    }

    //自定义异常处理HwjException
    @ExceptionHandler(HwjException.class)
    @ResponseBody
    public Result error(HwjException e) {
        e.printStackTrace();
        return Result.fail(null).code(e.getCode()).message(e.getMsg());
    }

}
