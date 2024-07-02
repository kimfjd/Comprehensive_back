//DatingAppService.java
package com.sick.apeuda.service;


import com.sick.apeuda.dto.MemberDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.entity.UnlikeMember;
import com.sick.apeuda.repository.DatingAppRepository;
import com.sick.apeuda.repository.UnlikeMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatingAppService {

    @Autowired
    private DatingAppRepository datingAppRepository;
    @Autowired
    private UnlikeMemberRepository unlikeMemberRepository;

    // 본인을 제외한 유저 리스트 출력
    public List<MemberDto> getFilteredMemberList(String currentUserEmail) {
        List<Member> members = datingAppRepository.findFilteredMember(currentUserEmail);
        return members.stream()
                .limit(5) // 5개의 유저만 가져오기
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MemberDto convertToDto(Member member) {
        MemberDto dto = new MemberDto();
        dto.setEmail(member.getEmail());
        dto.setNickname(member.getNickname());
        dto.setProfileImgPath(member.getProfileImgPath());
        dto.setSkill(member.getSkill());
        dto.setMyInfo(member.getMyInfo());
        return dto;
    }
    // 싫어하는 유저 저장
    @Transactional
    public void saveUnlikeMember(String memberEmail, String unlikeMemberEmail) {
        Member member = datingAppRepository.findById(memberEmail).orElseThrow();
        Member unlikeMember = datingAppRepository.findById(unlikeMemberEmail).orElseThrow();
        UnlikeMember unlike = new UnlikeMember();
        unlike.setMember(member);
        unlike.setUnlikeMember(unlikeMember);
        unlike.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        unlikeMemberRepository.save(unlike);
        System.out.println("Unlike member saved: " + unlike.toString());
    }
}
