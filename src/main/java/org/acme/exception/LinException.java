package org.acme.exception;

public class LinException extends RuntimeException{
    private final Integer code;

    public Integer getCode() {
        return code;
    }

    public LinException(Integer code, String message){
        super(message);
        this.code=code;
    }
}
