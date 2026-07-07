package com.bjorbun.billiard.service;

import com.bjorbun.billiard.model.Meja;
import com.bjorbun.billiard.model.Pemesanan;
import com.bjorbun.billiard.repository.MejaRepository;
import com.bjorbun.billiard.repository.PemesananRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PemesananService {
    
    private final PemesananRepository pemesananRepository;

    // Kita panggil MejaRepository agar bisa mengambil data utuh dari database
    private final MejaRepository mejaRepository; 

    public List<Pemesanan> getAllPemesanan() { 
        return pemesananRepository.findAll(); 
    }
    
    public Pemesanan buatPesanan(Pemesanan pesanan) {
        // 1. Cek apakah di dalam JSON ada data meja
        if (pesanan.getMeja() != null && pesanan.getMeja().getId() != null) {
            
            // 2. Ambil data meja asli dari database berdasarkan ID
            Meja mejaAsli = mejaRepository.findById(pesanan.getMeja().getId()).orElse(null);
            
            // 3. Jika meja asli ditemukan, hitung total biaya menggunakan harga dari database
            if (mejaAsli != null && pesanan.getDurasiJam() != null) {
                Double harga = mejaAsli.getHargaPerJam();
                pesanan.setTotalBiaya(harga * pesanan.getDurasiJam());
                
                // Pastikan pesanan tersambung dengan meja asli yang datanya lengkap
                pesanan.setMeja(mejaAsli);
            }
        }
        return pemesananRepository.save(pesanan);
    }
}