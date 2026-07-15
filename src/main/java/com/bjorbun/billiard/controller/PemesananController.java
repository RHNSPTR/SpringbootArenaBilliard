package com.bjorbun.billiard.controller;

import com.bjorbun.billiard.model.Pemesanan;
import com.bjorbun.billiard.service.PemesananService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pemesanan")
@RequiredArgsConstructor
public class PemesananController {

    private final PemesananService pemesananService;

    @GetMapping
    public List<Pemesanan> getPemesananList() {
        return pemesananService.getAllPemesanan();
    }

    @PostMapping
    public ResponseEntity<?> checkIn(@RequestBody Pemesanan pemesanan) {
        try {
            Pemesanan baru = pemesananService.buatPemesanan(pemesanan);
            return ResponseEntity.ok(baru);
        } catch (RuntimeException e) {
            // Mengembalikan pesan eror jika meja penuh atau terisi
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ENDPOINT BARU: Membalkan Pemesanan (Mengubah status transaksi & membebaskan meja)
    @PutMapping("/{id}/batal")
    public ResponseEntity<?> batalPemesanan(@PathVariable Long id) {
        try {
            Pemesanan batal = pemesananService.batalkanPemesanan(id);
            return ResponseEntity.ok(batal);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ENDPOINT BARU: Menghapus data Booking permanen dari database
    @DeleteMapping("/{id}")
    public ResponseEntity<?> hapusBooking(@PathVariable Long id) {
        try {
            pemesananService.hapusPemesanan(id);
            return ResponseEntity.ok("Data booking dengan ID " + id + " berhasil dihapus.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}