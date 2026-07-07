package com.bjorbun.billiard.controller;

import com.bjorbun.billiard.model.Pemesanan;
import com.bjorbun.billiard.service.PemesananService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pemesanan")
@RequiredArgsConstructor
public class PemesananController {
    private final PemesananService pemesananService;

    @GetMapping
    public List<Pemesanan> getListPesanan() { return pemesananService.getAllPemesanan(); }

    @PostMapping
    public Pemesanan checkIn(@RequestBody Pemesanan pesanan) { 
        return pemesananService.buatPesanan(pesanan); 
    }
}