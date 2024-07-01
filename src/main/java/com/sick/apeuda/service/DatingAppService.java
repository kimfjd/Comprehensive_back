package com.sick.apeuda.service;


import com.sick.apeuda.dto.MemberDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.repository.DatingAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatingAppService {

    @Autowired
    private DatingAppRepository datingAppRepository;

    // 본인을 제외한 유저 리스트 출력

    public List<MemberDto> getMemberListExcludingCurrentUserAndFriends(String currentUserEmail) {
        List<Member> members = datingAppRepository.findMembersExcludingCurrentUserAndFriends(currentUserEmail);
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
}
