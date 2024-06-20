package com.sick.apeuda.controller;

import com.sick.apeuda.dto.BoardDto;
import com.sick.apeuda.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    // 게시글 상세 조회 (jwt키로 확인 필요)
    @GetMapping("/detail/{id}")
    public ResponseEntity<BoardDto> boardDetail(@PathVariable Long id) {
        log.warn("board id : " + id);
        BoardDto boardDto = boardService.getBoardDetail(id);
        return ResponseEntity.ok(boardDto);
    }
}
