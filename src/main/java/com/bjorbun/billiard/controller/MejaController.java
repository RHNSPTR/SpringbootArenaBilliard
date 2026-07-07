package com.bjorbun.billiard.controller;

import com.bjorbun.billiard.dto.MejaRequestDTO;
import com.bjorbun.billiard.model.Meja;
import com.bjorbun.billiard.service.MejaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/meja")
@RequiredArgsConstructor
public class MejaController {

    private final MejaService mejaService;

    // 1. READ ALL - GET http://localhost:8080/api/meja
    @GetMapping
    public List<Meja> getMejaList() {
        return mejaService.getAllMeja();
    }

    // 2. READ BY ID - GET http://localhost:8080/api/meja/1
    @GetMapping("/{id}")
    public ResponseEntity<Meja> getMejaById(@PathVariable Long id) {
        return mejaService.getMejaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. CREATE - POST http://localhost:8080/api/meja
    @PostMapping
    public Meja createMeja(@Valid @RequestBody MejaRequestDTO mejaDTO) {
        // Proses memindahkan data dari DTO ke Entity
        Meja mejaBaru = new Meja();
        mejaBaru.setNoMeja(mejaDTO.getNoMeja());
        mejaBaru.setTipeMeja(mejaDTO.getTipeMeja());
        mejaBaru.setStatus(mejaDTO.getStatus());
        mejaBaru.setHargaPerJam(mejaDTO.getHargaPerJam());
        
        return mejaService.saveMeja(mejaBaru);
    }

    // 4. UPDATE - PUT http://localhost:8080/api/meja/1
    @PutMapping("/{id}")
    public Meja updateMeja(@PathVariable Long id, @RequestBody Meja meja) {
        return mejaService.updateMeja(id, meja);
    }

    // 5. DELETE - DELETE http://localhost:8080/api/meja/1
    @DeleteMapping("/{id}")
    public String deleteMeja(@PathVariable Long id) {
        mejaService.deleteMeja(id);
        return "Data Meja dengan ID " + id + " berhasil dihapus!";
    }
}