package com.review.paper_review.presentation.controller;

import com.review.paper_review.domain.exception.PostNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        logger.error("오류 발생: ", ex);
        model.addAttribute("message", "문제가 발생했습니다.");
        return "error";
    }

    @ExceptionHandler(PostNotFoundException.class)
    public String handleEntityNotFoundException(PostNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

}
