package com.bjorbun.billiard.service;

import com.bjorbun.billiard.model.Meja;
import com.bjorbun.billiard.model.Pemesanan;
import com.bjorbun.billiard.repository.MejaRepository;
import com.bjorbun.billiard.repository.PemesananRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PemesananService {

    private final PemesananRepository pemesananRepository;
    private final MejaRepository mejaRepository;

    public List<Pemesanan> getAllPemesanan() {
        return pemesananRepository.findAll();
    }

    @Transactional
    public Pemesanan buatPemesanan(Pemesanan pemesanan) {
        if (pemesanan.getMeja() == null || pemesanan.getMeja().getId() == null) {
            throw new RuntimeException("Meja harus dipilih terlebih dahulu.");
        }

        if (pemesanan.getDurasiJam() == null || pemesanan.getDurasiJam() <= 0) {
            throw new RuntimeException("Durasi booking harus lebih dari 0 jam.");
        }

        // 1. Cari data meja asli dari database
        Meja meja = mejaRepository.findById(pemesanan.getMeja().getId())
                .orElseThrow(() -> new RuntimeException("Meja dengan ID tersebut tidak ditemukan"));

        // 2. LOGIKA PERBAIKAN: Cek apakah meja penuh atau terisi
        if ("Terisi".equalsIgnoreCase(meja.getStatus()) || "Penuh".equalsIgnoreCase(meja.getStatus())) {
            throw new RuntimeException("Gagal Booking! Meja " + meja.getNoMeja() + " sedang penuh atau digunakan.");
        }

        // 3. Hitung otomatis total biaya berdasarkan harga per jam meja
        pemesanan.setTotalBiaya(meja.getHargaPerJam() * pemesanan.getDurasiJam());
        pemesanan.setStatusPemesanan("Mulai");
        pemesanan.setWaktuMulai(LocalDateTime.now());

        // 4. Ubah status meja menjadi Terisi lalu simpan ke database
        meja.setStatus("Terisi");
        mejaRepository.save(meja);

        return pemesananRepository.save(pemesanan);
    }

    @Transactional
    public Pemesanan batalkanPemesanan(Long id) {
        // 1. Cari data booking yang mau dibatalkan
        Pemesanan pemesanan = pemesananRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data pemesanan tidak ditemukan"));

        // 2. LOGIKA PERBAIKAN: Ubah status transaksi menjadi Dibatalkan
        pemesanan.setStatusPemesanan("Dibatalkan");

        // 3. Kembalikan status meja terkait menjadi Tersedia kembali
        Meja meja = pemesanan.getMeja();
        if (meja != null) {
            meja.setStatus("Tersedia");
            mejaRepository.save(meja);
        }

        return pemesananRepository.save(pemesanan);
    }

    @Transactional
    public void hapusPemesanan(Long id) {
        // 1. Cari data booking yang mau di-delete
        Pemesanan pemesanan = pemesananRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data pemesanan tidak ditemukan"));

        // 2. LOGIKA PERBAIKAN: Jika booking dihapus saat statusnya masih aktif/Mulai, bebaskan mejanya
        if ("Mulai".equalsIgnoreCase(pemesanan.getStatusPemesanan())) {
            Meja meja = pemesanan.getMeja();
            if (meja != null) {
                meja.setStatus("Tersedia");
                mejaRepository.save(meja);
            }
        }

        // 3. Hapus permanen dari database
        pemesananRepository.deleteById(id);
    }
}