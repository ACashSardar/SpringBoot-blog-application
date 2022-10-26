package com.akash.blog.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ModelAndView resourceNotFoundExceptionHandler() {
		return new ModelAndView("Error404","message","Oops!! RESOURCE NOT AVAILABE");
	}
}
