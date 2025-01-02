package com.review.paper_review.presentation.controller;

import com.review.paper_review.domain.exception.PostNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        log.error("오류 발생: ", ex);
        model.addAttribute("message", "문제가 발생했습니다.");
        return "error";
    }

    @ExceptionHandler(PostNotFoundException.class)
    public String handleEntityNotFoundException(PostNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

}
