package com.not_projesi.controller.impl;

import com.not_projesi.entity.RootEntity;

public class RestBaseController {

    public <T> RootEntity<T> ok(T data){

        return RootEntity.ok(data);

    }

    public <T> RootEntity<T> error(String errorMessage){

        return RootEntity.error(errorMessage);

    }

}
