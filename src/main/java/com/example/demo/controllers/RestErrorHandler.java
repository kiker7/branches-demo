package com.example.demo.controllers;

import com.example.demo.exception.BranchNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class RestErrorHandler {

    @ResponseBody
    @ExceptionHandler(BranchNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String branchNotFoundHandler(BranchNotFoundException ex){
        log.error("Branch not found exception: " + ex.getMessage());
        return ex.getMessage();
    }
}
