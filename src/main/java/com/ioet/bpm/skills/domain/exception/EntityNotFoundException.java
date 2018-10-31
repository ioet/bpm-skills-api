package com.ioet.bpm.skills.domain.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
