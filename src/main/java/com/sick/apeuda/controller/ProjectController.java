package com.sick.apeuda.controller;

import com.sick.apeuda.dto.BoardReqDto;
import com.sick.apeuda.dto.ProjectReqDto;
import com.sick.apeuda.dto.ProjectResDto;
import com.sick.apeuda.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    // 플젝 게시판 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<ProjectReqDto>> projectBoardList() {
        log.info("projectBoardList실행");
        List<ProjectReqDto> list = projectService.getProjectList();
        return ResponseEntity.ok(list);
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
