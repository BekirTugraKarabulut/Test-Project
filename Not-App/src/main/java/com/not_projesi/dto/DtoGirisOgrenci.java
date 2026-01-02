package com.not_projesi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoGirisOgrenci {

    @NotNull(message = "ID alanı boş geçilemez!")
    @Min(value = 2000000000 , message = "ID değerini doğru giriniz!")
    @Max(value = 3000000000L , message = "ID değerini doğru giriniz!")
    private Long id;

    @NotNull(message = "Şifre alanı boş geçilemez!")
    private String ogrenciSifre;


}
