package com.review.paper_review.domain.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) { super(message); }

}
