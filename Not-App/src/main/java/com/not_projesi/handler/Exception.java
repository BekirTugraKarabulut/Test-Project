package com.not_projesi.handler;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Exception<E> {

    private String localhost;

    private Date date;

    private String path;

    private E mesaj;

}
