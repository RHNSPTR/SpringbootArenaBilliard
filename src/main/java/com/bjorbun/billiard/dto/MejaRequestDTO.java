package com.bjorbun.billiard.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MejaRequestDTO {
    
    @NotBlank(message = "Nomor meja tidak boleh kosong")
    private String noMeja;

    @NotBlank(message = "Tipe meja tidak boleh kosong")
    private String tipeMeja;

    @NotBlank(message = "Status tidak boleh kosong")
    private String status;

    @NotNull(message = "Harga per jam tidak boleh kosong")
    @Min(value = 10000, message = "Harga per jam minimal Rp 10.000")
    private Double hargaPerJam;
}