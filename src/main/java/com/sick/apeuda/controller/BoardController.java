package com.sick.apeuda.controller;

import com.sick.apeuda.dto.BoardDto;
import com.sick.apeuda.dto.ProjectDto;
import com.sick.apeuda.entity.Skill;
import com.sick.apeuda.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    // 게시글 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<BoardDto>> boardList() {
        log.info("boardList실행");
        List<BoardDto> list = boardService.getBoardList();
        return ResponseEntity.ok(list);
    }
    // 자유 게시글 상세 조회
    @GetMapping("/detail/{id}")
    public ResponseEntity<BoardDto> boardDetail(@PathVariable Long id) {
        log.warn("board id : " + id);
        BoardDto boardDto = boardService.getBoardDetail(id);
        return ResponseEntity.ok(boardDto);
    }
    // 자유 게시글 등록
    @PostMapping("/insert")
    public ResponseEntity<Boolean> boardInsert(@RequestBody BoardDto boardDto){
        boolean isTrue = boardService.saveBoard(boardDto);
        return ResponseEntity.ok(isTrue);
    }
    // 자유 게시판 삭제
    @GetMapping("/delete")
    public ResponseEntity<Boolean> delboard(@RequestParam Long id){
        System.out.println("삭제 하는 게시판 넘버 : " + id);
        boolean isTrue = boardService.delboard(id);
        return ResponseEntity.ok(isTrue);

    }

}
