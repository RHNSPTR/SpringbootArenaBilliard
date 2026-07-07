package com.bjorbun.billiard.service;

import com.bjorbun.billiard.model.Meja;
import com.bjorbun.billiard.repository.MejaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MejaService {
    
    private final MejaRepository mejaRepository;

    // READ (All)
    public List<Meja> getAllMeja() {
        return mejaRepository.findAll();
    }

    // READ (By ID)
    public Optional<Meja> getMejaById(Long id) {
        return mejaRepository.findById(id);
    }

    // CREATE
    public Meja saveMeja(Meja meja) {
        return mejaRepository.save(meja);
    }
    
    // UPDATE
    public Meja updateMeja(Long id, Meja detailMeja) {
        Optional<Meja> mejaLama = mejaRepository.findById(id);
        
        if (mejaLama.isPresent()) {
            Meja mejaBaru = mejaLama.get();
            mejaBaru.setNoMeja(detailMeja.getNoMeja());
            mejaBaru.setTipeMeja(detailMeja.getTipeMeja());
            mejaBaru.setStatus(detailMeja.getStatus());
            mejaBaru.setHargaPerJam(detailMeja.getHargaPerJam());
            return mejaRepository.save(mejaBaru);
        }

        throw new IllegalArgumentException("Meja dengan ID " + id + " tidak ditemukan");
    }

    // DELETE
    public void deleteMeja(Long id) {
        mejaRepository.deleteById(id);
    }
}