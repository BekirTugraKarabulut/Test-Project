package com.not_projesi.exception;

import lombok.Getter;

@Getter
public enum MesajTipi {

    ARANAN_OGRENCI("5800","Böyle bir öğrenci sistemde yoktur."),
    KAYİT_YOK("1001","Ogrenci ID Hatalı !!"),
    HATALİ_ID("999","Hatalı ID girişi"),
    DERS_NOTU_YOKTUR("666","Ders Notu Yoktur.");

    private String code;
    private String mesaj;

    MesajTipi(String code, String mesaj) {
        this.code = code;
        this.mesaj = mesaj;
    }

}
