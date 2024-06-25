package com.sick.apeuda.controller;

import com.sick.apeuda.dto.ProjectDto;
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
    public ResponseEntity<List<ProjectDto>> projectBoardList() {
        log.info("projectBoardList실행");
        List<ProjectDto> list = projectService.getProjectBoardList();
        return ResponseEntity.ok(list);
    }
    // 플젝 게시글 등록
    @PostMapping("/insert")
    public ResponseEntity<Boolean> projectBoardInsert(@RequestBody ProjectDto projectDto){
        boolean isTrue = projectService.saveProjectBoard(projectDto);
        return ResponseEntity.ok(isTrue);
    }
    @PostMapping("/reply/insert")
    public ResponseEntity<Boolean> replyInsert(@RequestBody ProjectDto projectDto){
        boolean isTrue = projectService.saveProjectBoard(projectDto);
        return ResponseEntity.ok(isTrue);
    }

    @GetMapping("/delete")
    public ResponseEntity<Boolean> delproject(@RequestParam Long id){
        boolean isTrue = projectService.delproject(id);
        return ResponseEntity.ok(isTrue);

    }
}
