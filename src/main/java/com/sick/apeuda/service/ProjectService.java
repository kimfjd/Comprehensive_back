package com.sick.apeuda.service;


import com.sick.apeuda.dto.ProjectReqDto;
import com.sick.apeuda.dto.ProjectResDto;
import com.sick.apeuda.dto.ReplyDto;
import com.sick.apeuda.entity.Project;
import com.sick.apeuda.entity.Reply;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.repository.ProjectRepository;
import com.sick.apeuda.repository.ReplyRepository;
import com.sick.apeuda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.sick.apeuda.security.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    /**
     * 프로젝트 게시판 전체 조회 메소드
     * @return projectDtos Project 엔티티타입의 List 반환
     */
    public List<ProjectReqDto> getProjectBoardList() {
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
        projectReqDto.setProjectPassword(project.getProjectPassword());
        projectReqDto.setProjectTime(LocalDateTime.now());

        return projectReqDto;
    }

    /**
     * 플젝 글등록
     * @param projectResDto
     * @return true 등록 성공 실패 반환
     */
    public boolean saveProject(ProjectReqDto projectResDto ) {
        try{
            Project project = new Project();
            String userId = getCurrentMemberId();
            // 토큰 발급 받기전까지 Id 직접입력 토큰 발급시 userId 넣으면됨
            User user = userRepository.findById("testId@gmail.com").orElseThrow(
                    () -> new RuntimeException("User does not exist")
            );
            project.setProjectId(projectResDto.getProjectId());
            project.setJob(projectResDto.getJob());
            project.setProjectName(projectResDto.getProjectName());
            project.setProjectTitle(projectResDto.getProjectTitle());
            project.setProjectContent(projectResDto.getProjectContent());
            project.setProjectPassword(projectResDto.getProjectPassword());
            project.setImgPath(project.getImgPath());
            project.setProjectTime(LocalDateTime.now());
            project.setSkills(projectResDto.getSkillName());
            project.setUser(user);
            project.setNickName(user.getNickname());
            project.setProfileImage(user.getProfileImgPath());
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
        projectResDto.setProjectId(project.getProjectId());
        projectResDto.setJob(project.getJob());
        projectResDto.setProjectName(project.getProjectName());
        projectResDto.setProjectTitle(project.getProjectTitle());
        //projectDto.setProjectPassword(project.getProjectPassword());
        projectResDto.setProjectContent(project.getProjectContent());
        projectResDto.setProjectTime(LocalDateTime.now());

        // 플젝 작성자 정보 설정
        projectResDto.setNickName(project.getUser().getNickname());
        projectResDto.setProfileImg(project.getUser().getProfileImgPath());

        // 댓글 리스트 조회 및 설정
        List<Reply> replies = replyRepository.findByProjectId(project.getProjectId());
        List<ReplyDto> replyDtos = convertEntityListToDtoList(replies);
        projectResDto.setReplies(replyDtos);
        return projectResDto;
    }
    /**
     * 댓글 엔티티를 DTO로 변환
     * @param reply Reply 엔티티 객체
     * @return ReplyDto -> 게시판 번호에 맞는 댓글 리스트
     */
    private ReplyDto saveReplyList(Reply reply) {
        ReplyDto replyDto = new ReplyDto();
        replyDto.setContent(reply.getContent());
        replyDto.setProfile_img(reply.getUser().getProfileImgPath());
        replyDto.setNickName(reply.getUser().getNickname());
        replyDto.setRegDate(reply.getRegDate());
        return replyDto;
    }
    private List<ReplyDto> convertEntityListToDtoList(List<Reply> replies) {
        return replies.stream()
                .map(this::saveReplyList)
                .collect(Collectors.toList());
    }

}
