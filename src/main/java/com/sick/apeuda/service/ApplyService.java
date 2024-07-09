package com.sick.apeuda.service;

import com.sick.apeuda.dto.ApplyReqDto;
import com.sick.apeuda.dto.ApplyResDto;
import com.sick.apeuda.entity.Apply;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.entity.Project;
import com.sick.apeuda.repository.ApplyRepository;
import com.sick.apeuda.repository.ChatManageRepository;
import com.sick.apeuda.repository.MemberRepository;
import com.sick.apeuda.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sick.apeuda.security.SecurityUtil.getCurrentMemberId;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final ChatManageRepository chatManageRepository;
    // 신청 조회
    public List<ApplyResDto> getApplyList(Long projectId){
        List<Apply> applies = applyRepository.findByProjectId(projectId);
        List<ApplyResDto> applyResDtos = new ArrayList<>();
        for(Apply apply : applies) {
            applyResDtos.add(convertEntityToDto(apply));
        }
        return applyResDtos;
    }
    private ApplyResDto convertEntityToDto(Apply apply) {
        ApplyResDto applyResDto = new ApplyResDto();
        applyResDto.setApplyId(apply.getApplyId());
        applyResDto.setApplyStatus(apply.getApplyStatus());
        applyResDto.setApplicant(apply.getMember().getEmail());
        applyResDto.setMemberId(apply.getProject().getMember().getEmail());
        System.out.println("등록자 아이디 : "+apply.getProject().getMember().getEmail());
        applyResDto.setProjectId(apply.getProject().getProjectId());
        return applyResDto;
    }
    // 신청 등록
    public boolean saveApply(ApplyReqDto applyReqDto) {
        try {
            Apply apply = new Apply();
            Project project  = projectRepository.findById(applyReqDto.getProjectId()).orElseThrow(
                    () -> new RuntimeException("프로젝트 번호가 존재하지않습니다.")
            );
            Long rs = project.getProjectId();
            String memberId = getCurrentMemberId();
            System.out.println("토큰으로 받은 멤버아이디 체크 : " +memberId );
            Member member = memberRepository.findById(memberId).orElseThrow(
                    () -> new RuntimeException("Member does not exist")
            );
            apply.setApplyId(applyReqDto.getApplyId());
            apply.setApplyStatus(applyReqDto.getApplyStatus());
            apply.setApplyDate(LocalDateTime.now());
            apply.setMember(member);
            //apply.setMember(applyReqDto.getApplicant());
            apply.setProject(project);
            applyRepository.save(apply);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //
    public boolean acceptApply(boolean result){
        try {

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
