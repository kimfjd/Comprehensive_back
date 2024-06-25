package com.sick.apeuda.service;

import com.sick.apeuda.dto.ProjectDto;
import com.sick.apeuda.entity.Project;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.repository.ProjectRepository;
import com.sick.apeuda.repository.UserRepository;
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
    private final UserRepository userRepository;

    /**
     * 프로젝트 게시판 전체 조회 메소드
     * @return projectDtos Project 엔티티타입의 List 반환
     */
    public List<ProjectDto> getProjectBoardList() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectDto> projectDtos = new ArrayList<>();
        for(Project project : projects) {
            projectDtos.add(convertProjectEntityToDto(project));
        }
        return projectDtos;
    }

    public boolean delproject(Long id) {
        try{
            projectRepository.deleteById(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 플젝 게시글 엔티티를 DTO로 변환(플젝 게시글 입력)
     * @param project Project 엔티티 타입
     * @return projectDto -> 게시판 전체 리스트 반환
     */
    private ProjectDto convertProjectEntityToDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(project.getProjectId());
        projectDto.setJob(project.getJob());
        projectDto.setProjectName(project.getProjectName());
        projectDto.setProjectTitle(project.getProjectTitle());
        projectDto.setProjectPassword(project.getProjectPassword());
        projectDto.setProjectTime(LocalDateTime.now());
        projectDto.setEmail(project.getUser().getEmail());
        return projectDto;
    }

    public boolean saveProjectBoard(ProjectDto projectDto ) {
        try{
            Project project = new Project();
            String userId = getCurrentMemberId();
            // 토큰 발급 받기전까지 Id 직접입력 토큰 발급시 userId 넣으면됨
            User user = userRepository.findById("testId@gmail.com").orElseThrow(
                    () -> new RuntimeException("User does not exist")
            );
            project.setProjectId(projectDto.getProjectId());
            project.setJob(projectDto.getJob());
            project.setProjectName(projectDto.getProjectName());
            project.setProjectTitle(projectDto.getProjectTitle());
            project.setProjectContent(projectDto.getProjectContent());
            project.setProjectPassword(projectDto.getProjectPassword());
            project.setImgPath(project.getImgPath());
            project.setProjectTime(LocalDateTime.now());
            project.setSkills(projectDto.getSkillName());
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
     * 플젝 상세내용 엔티티를 DTO로 변환(플젝 상세 내용, 스킬 조회)
     * @param project Project 엔티티 타입
     * @return projectDto -> 게시판 전체 리스트 반환
     */
    private ProjectDto convertProjectDetailEntityToDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(project.getProjectId());
        projectDto.setJob(project.getJob());
        projectDto.setProjectName(project.getProjectName());
        projectDto.setProjectTitle(project.getProjectTitle());
        projectDto.setProjectPassword(project.getProjectPassword());
        projectDto.setProjectTime(LocalDateTime.now());
        return projectDto;
    }
}
