package com.sick.apeuda.controller;

import com.sick.apeuda.dto.BoardReqDto;
import com.sick.apeuda.dto.ProjectReqDto;
import com.sick.apeuda.dto.ProjectResDto;
import com.sick.apeuda.dto.ReadMessageDto;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    // 플젝 게시판 전체 조회
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> projectBoardList(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "7") int size) {

        Map<String, Object>rs = projectService.getProjectList(page,size);

        return ResponseEntity.ok(rs);
    }

    @GetMapping("/mypj")
    public ResponseEntity<List<ProjectReqDto>> myProjectList() {
        List<ProjectReqDto> friends = projectService.getMyProject();
        return ResponseEntity.ok(friends);
    }


    // 플젝 게시글 상세 조회
    @GetMapping("/detail/{id}")
    public ResponseEntity<ProjectResDto> projectDetail(@PathVariable Long id) {
        log.warn("project id : " + id);
        ProjectResDto projectResDto = projectService.getProjectDetail(id);
        return ResponseEntity.ok(projectResDto);
    }
    // 플젝 게시글 등록
    @PostMapping("/insert")
    public ResponseEntity<Boolean> projectBoardInsert(@RequestBody ProjectReqDto projectReqDto){
        System.out.println("projectBoardInsert: " + projectReqDto);
        System.out.println("ChatRoom ID: " + projectReqDto.getChatRoom().getRoomId());
        boolean isTrue = projectService.saveProject(projectReqDto);
        return ResponseEntity.ok(isTrue);
    }
    // 플젝 게시판 삭제
    @GetMapping("/delete")
    public ResponseEntity<Boolean> delProject(@RequestParam Long id){
        System.out.println("삭제 하는 게시판 넘버 : " + id);
        boolean isTrue = projectService.delProject(id);
        return ResponseEntity.ok(isTrue);
    }

    // 자유 게시글 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<Boolean> boardModify(@PathVariable Long id, @RequestBody ProjectReqDto projectReqDto) {
        boolean isTrue = projectService.modifyProject(id, projectReqDto);
        return ResponseEntity.ok(isTrue);
    }

}
