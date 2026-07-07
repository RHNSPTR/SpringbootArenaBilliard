package com.bjorbun.billiard.controller;

import com.bjorbun.billiard.model.Member;
import com.bjorbun.billiard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public List<Member> getMembers() { return memberService.getAllMember(); }

    @PostMapping
    public Member addMember(@RequestBody Member member) { return memberService.saveMember(member); }
}