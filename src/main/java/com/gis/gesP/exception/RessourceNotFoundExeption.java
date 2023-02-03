package com.gis.gesP.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RessourceNotFoundExeption extends RuntimeException{

    public RessourceNotFoundExeption(String message){
        super(message);
    }
}
