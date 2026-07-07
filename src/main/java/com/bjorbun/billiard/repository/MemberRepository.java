package com.bjorbun.billiard.repository;

import com.bjorbun.billiard.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}