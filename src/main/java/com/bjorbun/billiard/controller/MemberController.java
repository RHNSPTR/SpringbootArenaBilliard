package com.bjorbun.billiard.controller;

import com.bjorbun.billiard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // Tambahkan endpoint delete ini di bagian bawah class
    @DeleteMapping("/{id}")
    public ResponseEntity<?> hapusMember(@PathVariable Long id) {
        try {
            memberService.hapusMember(id);
            return ResponseEntity.ok("Member dengan ID " + id + " berhasil dihapus dari sistem.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}