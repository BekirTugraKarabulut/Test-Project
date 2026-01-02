package com.not_projesi.exception;

public class BaseException extends RuntimeException {

    public BaseException() {

    }

    public BaseException(ErrorMesaj errorMesaj) {
        super(errorMesaj.prepareHataMesaji());
    }

}