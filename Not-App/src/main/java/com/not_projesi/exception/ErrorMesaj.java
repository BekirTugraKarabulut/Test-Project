package com.not_projesi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMesaj {

    private MesajTipi mesajTipi;
    private String ofStatic;

    public String prepareHataMesaji(){

        StringBuilder builder = new StringBuilder();
        builder.append(mesajTipi.getMesaj());
        if(ofStatic != null){
            builder.append(" : " + ofStatic);
        }
        return builder.toString();
    }

}
