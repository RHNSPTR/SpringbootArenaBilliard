package com.bjorbun.billiard.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pemesanan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String kodeBooking;
    private Integer durasiJam;
    private Double totalBiaya;
    private LocalDateTime waktuMulai;

    // Relasi Many-to-One ke Member
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // Relasi Many-to-One ke Meja
    @ManyToOne
    @JoinColumn(name = "meja_id")
    private Meja meja;
}