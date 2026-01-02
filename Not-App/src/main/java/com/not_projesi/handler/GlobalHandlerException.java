package com.not_projesi.handler;

import com.not_projesi.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiError> GlobalHandlerException(BaseException exception , WebRequest request){

        return ResponseEntity.badRequest().body(createApiError(exception.getMessage() , request));

    }

    public <E> ApiError<E> createApiError(E mesaj , WebRequest request){

        ApiError<E> apiError = new ApiError<>();

        apiError.setStatus(HttpStatus.BAD_REQUEST.value());

        Exception<E> exception = new Exception<>();
        exception.setLocalhost(getLocalHost());
        exception.setDate(new Date());
        exception.setPath(request.getDescription(false).substring(4));
        exception.setMesaj(mesaj);

        apiError.setException(exception);

        return apiError;
    }

    public String getLocalHost(){

        try {
           return InetAddress.getLocalHost().getHostName();
        }catch (UnknownHostException e){
            System.out.println("Local Host Bulunamadı.");
        }

        return null;
    }

}