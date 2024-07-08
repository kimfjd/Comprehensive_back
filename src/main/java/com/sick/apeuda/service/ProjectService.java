package com.sick.apeuda.service;


import com.sick.apeuda.dto.ProjectReqDto;
import com.sick.apeuda.dto.ProjectResDto;
import com.sick.apeuda.entity.ChatRoom;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.entity.Project;
import com.sick.apeuda.repository.MemberRepository;
import com.sick.apeuda.repository.ProjectRepository;
import com.sick.apeuda.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sick.apeuda.security.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;

    /**
     * 프로젝트 게시판 전체 조회 메소드
     * @return projectDtos Project 엔티티타입의 List 반환
     */
    public List<ProjectReqDto> getProjectList() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectReqDto> projectDtos = new ArrayList<>();
        for(Project project : projects) {
            projectDtos.add(convertProjectEntityToDto(project));
        }
        return projectDtos;
    }

    /**
     * 플젝 게시글 엔티티를 DTO로 변환(플젝 게시글 입력)
     * @param project Project 엔티티 타입
     * @return projectDto -> 게시판 전체 리스트 반환
     */
    private ProjectReqDto convertProjectEntityToDto(Project project) {
        ProjectReqDto projectReqDto = new ProjectReqDto();
        projectReqDto.setProjectId(project.getProjectId());
        projectReqDto.setJob(project.getJob());
        projectReqDto.setProjectName(project.getProjectName());
        projectReqDto.setProjectTitle(project.getProjectTitle());
        projectReqDto.setProjectContent(project.getProjectContent());
        projectReqDto.setProjectPassword(project.getProjectPassword());
        projectReqDto.setProjectTime(project.getProjectTime());
        projectReqDto.setRegDate(project.getRegDate());
        projectReqDto.setEmail(project.getMember().getEmail());
        projectReqDto.setNickName(project.getMember().getNickname());
        projectReqDto.setProfileImg(project.getMember().getProfileImgPath());
        projectReqDto.setSkillName(project.getSkills());
        return projectReqDto;
    }
    /**
     * 플젝 글등록
     * @param projectReqDto
     * @return true 등록 성공 실패 반환
     */
    public boolean saveProject(ProjectReqDto projectReqDto) {
        try{
            Project project = new Project();

            String memberId = getCurrentMemberId();
            // 토큰 발급 받기전까지 Id 직접입력 토큰 발급시 memberId 넣으면됨
            System.out.println("토큰으로 받은 멤버아이디 체크 : " +memberId );
            Member member = memberRepository.findById(memberId).orElseThrow(
                    () -> new RuntimeException("Member does not exist")
            );
            System.out.println("projectReqDto.getChatRoom() : " + projectReqDto.getChatRoom());
            project.setProjectId(projectReqDto.getProjectId());
            //project.setJob(projectReqDto.getJob());
            project.setProjectName(projectReqDto.getProjectName());
            project.setProjectTitle(projectReqDto.getProjectTitle());
            project.setProjectContent(projectReqDto.getProjectContent());
            project.setProjectPassword(projectReqDto.getProjectPassword());
            project.setImgPath(projectReqDto.getImgPath());
            project.setProjectTime(projectReqDto.getProjectTime());
            //project.setRecruitNum(projectReqDto.getRecruitNum());
            project.setRegDate(LocalDateTime.now());
            project.setSkills(projectReqDto.getSkillName());
            project.setRecruitNum(projectReqDto.getRecruitNum());
            project.setMember(member);
            project.setNickName(member.getNickname());
            project.setProfileImage(member.getProfileImgPath());
            project.setChatRoom(projectReqDto.getChatRoom());
            projectRepository.save(project);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 플젝 상세 조회
     * @param id 게시판 고유번호
     * @return param으로 받은 id에 해당하는 BoardDto객체 값 반환
     */
    public ProjectResDto getProjectDetail(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );
        return convertProjectDetailEntityToDto(project);
    }
    // 게시글 수정
    // 게시글 수정
    public boolean modifyProject(Long id, ProjectReqDto projectReqDto) {
        try {
            Project project = projectRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Board does not exist")
            );
            String memberId = getCurrentMemberId();
            // 토큰 발급 받기전까지 Id 직접입력 토큰 발급시 memberId 넣으면됨
            System.out.println("토큰으로 받은 멤버아이디 체크 : " +memberId );
            Member member = memberRepository.findById(memberId).orElseThrow(
                    () -> new RuntimeException("Member does not exist")
            );
            project.setMember(member);
            project.setProjectName(projectReqDto.getProjectName());
            project.setProjectTitle(projectReqDto.getProjectTitle());
            project.setProjectContent(projectReqDto.getProjectContent());
            project.setProjectPassword(projectReqDto.getProjectPassword());
            project.setRecruitNum(projectReqDto.getRecruitNum());

            // 등록일자는 전달된 값 유지
            project.setRegDate(projectReqDto.getRegDate());

            // 프로젝트 시간도 전달된 값으로 설정
            project.setProjectTime(projectReqDto.getProjectTime());

            // 기존 스킬을 새로운 스킬로 설정
            project.setSkills(projectReqDto.getSkillName());

            projectRepository.save(project);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public boolean modifyProject(Long id, ProjectReqDto projectReqDto) {
//        try {
//            Project project = projectRepository.findById(id).orElseThrow(
//                    () -> new RuntimeException("Board does not exist")
//            );
//            project.setProjectId(projectReqDto.getProjectId());
//            project.setJob(projectReqDto.getJob());
//            project.setProjectName(projectReqDto.getProjectName());
//            project.setProjectTitle(projectReqDto.getProjectTitle());
//            project.setProjectContent(projectReqDto.getProjectContent());
//            project.setProjectPassword(projectReqDto.getProjectPassword());
//            project.setRecruitNum(projectReqDto.getRecruitNum());
//            project.setImgPath(project.getImgPath());
//            project.setRegDate(LocalDateTime.now());
//            project.setProjectTime(projectReqDto.getProjectTime());
//            project.setSkills(projectReqDto.getSkillName());
//            projectRepository.save(project);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    /**
     * 플젝 게시판 삭제
     * @param id
     * @return
     */
    public boolean delProject(Long id) {
        try{
            projectRepository.deleteById(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 플젝 상세내용 엔티티를 DTO로 변환(플젝 상세 내용, 스킬 조회)
     * @param project Project 엔티티 타입
     * @return projectResDto -> 플젝 게시판 상세 정보 반환
     */
    private ProjectResDto convertProjectDetailEntityToDto(Project project) {
        ProjectResDto projectResDto = new ProjectResDto();
        projectResDto.setMemberId(project.getMember());
        projectResDto.setProjectId(project.getProjectId());
        projectResDto.setJob(project.getJob());
        projectResDto.setProjectName(project.getProjectName());
        projectResDto.setProjectTitle(project.getProjectTitle());
        //projectDto.setProjectPassword(project.getProjectPassword());
        projectResDto.setProjectContent(project.getProjectContent());
        projectResDto.setRecruitNum(project.getRecruitNum());
        projectResDto.setRecruitMember(project.getRecruitMember());
        projectResDto.setProjectContent(project.getProjectContent());
        projectResDto.setProjectTime(project.getProjectTime());
        projectResDto.setRegDate(LocalDateTime.now());
        projectResDto.setSkillName(project.getSkills());

        // 플젝 작성자 정보 설정
        projectResDto.setNickName(project.getMember().getNickname());
        projectResDto.setProfileImg(project.getMember().getProfileImgPath());

        // 댓글 리스트 조회 및 설정
//        List<Reply> replies = replyRepository.findByProjectId(project.getProjectId());
//        List<ReplyDto> replyDtos = convertEntityListToDtoList(replies);
//        projectResDto.setReplies(replyDtos);
        return projectResDto;
    }
//    /**
//     * 댓글 엔티티를 DTO로 변환
//     * @param reply Reply 엔티티 객체
//     * @return ReplyDto -> 게시판 번호에 맞는 댓글 리스트
//     */
//    private ReplyDto saveReplyList(Reply reply) {
//        ReplyDto replyDto = new ReplyDto();
//        replyDto.setContent(reply.getContent());
//        replyDto.setProfile_img(reply.getMember().getProfileImgPath());
//        replyDto.setNickName(reply.getMember().getNickname());
//        replyDto.setRegDate(reply.getRegDate());
//        return replyDto;
//    }
//    private List<ReplyDto> convertEntityListToDtoList(List<Reply> replies) {
//        return replies.stream()
//                .map(this::saveReplyList)
//                .collect(Collectors.toList());
//    }

}
