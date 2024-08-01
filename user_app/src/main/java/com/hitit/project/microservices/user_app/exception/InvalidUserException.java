package com.hitit.project.microservices.user_app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidUserException extends RuntimeException{

    private final String msg;
}