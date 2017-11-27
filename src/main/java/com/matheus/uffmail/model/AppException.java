package com.matheus.uffmail.model;
/**
 *
 * @author matheus
 */

public class AppException extends Exception{
    private String error;
    
    public AppException(String msg) {
        super(msg);
        error = msg;
    }
    
    public String getError() {
        return error;
    }
}
