package com.amazing.editor.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

//统一异常处理
@ControllerAdvice
public class MyControllerAdvice {
//    @ResponseBody
//    @ExceptionHandler(value = Exception.class)
//    public Map<String, Object> exception(Exception e){
//        Map<String, Object> result = new HashMap<>();
//        result.put("code", -1);
//        result.put("msg", e.getMessage());
//        return result;
//    }
}
