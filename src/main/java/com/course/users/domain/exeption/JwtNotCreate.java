package com.course.users.domain.exeption;

public class JwtNotCreate extends RuntimeException {

    public JwtNotCreate(String error) {
        super(error);
    }
}
