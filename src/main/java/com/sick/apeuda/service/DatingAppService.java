package com.sick.apeuda.service;

import com.sick.apeuda.dto.MemberDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.entity.Subscription;
import com.sick.apeuda.entity.UnlikeMember;
import com.sick.apeuda.repository.DatingAppRepository;
import com.sick.apeuda.repository.SubscriptionRepository;
import com.sick.apeuda.repository.UnlikeMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatingAppService {

    @Autowired
    private DatingAppRepository datingAppRepository;
    @Autowired
    private UnlikeMemberRepository unlikeMemberRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    private final Map<String, Timestamp> nonSubscriberUsageMap = new HashMap<>();

    // 본인을 제외한 유저 리스트 출력
    public List<MemberDto> getFilteredMemberList(String currentUserEmail) {
        Member currentUser = datingAppRepository.findById(currentUserEmail).orElseThrow(
                () -> new RuntimeException("Member with email " + currentUserEmail + " does not exist")
        );

        // 구독 상태 확인
        Optional<Subscription> subscription = subscriptionRepository.findByMemberAndStatus(currentUser, "구독");

        if (subscription.isPresent()) {
            // 구독 상태인 경우 무제한
            return getMemberList(currentUserEmail, 5);
        } else {
            // 비구독 상태인 경우 사용 횟수 제한 및 24시간 후 재사용 가능
            Timestamp lastUsage = nonSubscriberUsageMap.get(currentUserEmail);
            if (lastUsage != null) {
                LocalDateTime lastUsageTime = lastUsage.toLocalDateTime();
                LocalDateTime currentTime = LocalDateTime.now();
                long hoursDifference = ChronoUnit.HOURS.between(lastUsageTime, currentTime);
                if (hoursDifference < 24) {
                    throw new RuntimeException("You have reached the maximum number of free usages. Please try again after 24 hours.");
                }
            }
            nonSubscriberUsageMap.put(currentUserEmail, Timestamp.valueOf(LocalDateTime.now()));
            return getMemberList(currentUserEmail, 5);
        }
    }

    private List<MemberDto> getMemberList(String currentUserEmail, int limit) {
        List<Member> members = datingAppRepository.findFilteredMember(currentUserEmail);
        return members.stream()
                .limit(limit)
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
        unlike.setTimestamp(new Timestamp(System.currentTimeMillis()));
        unlikeMemberRepository.save(unlike);
        System.out.println("Unlike member saved: " + unlike.toString());
    }
}
