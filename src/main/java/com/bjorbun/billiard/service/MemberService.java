package com.bjorbun.billiard.service;

import com.bjorbun.billiard.model.Member;
import com.bjorbun.billiard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<Member> getAllMember() { return memberRepository.findAll(); }
    public Member saveMember(Member member) { return memberRepository.save(member); }
    public void deleteMember(Long id) { memberRepository.deleteById(id); }
}