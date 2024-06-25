package com.sick.apeuda.controller;

import com.sick.apeuda.dto.BoardReqDto;
import com.sick.apeuda.dto.ReplyDto;
import com.sick.apeuda.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reply")
public class ReplyController {
    private final ReplyService replyService;

    /**
     * @param replyDto 댓글 Dto 객체
     * @return boolean 저장 성공 여부
     */
    @PostMapping("/insert")
    public ResponseEntity<Boolean> insertReply(@RequestBody ReplyDto replyDto){
        System.out.println("Reply Insert Controller execute");
        boolean isTrue = replyService.saveReply(replyDto);
        return ResponseEntity.ok(isTrue);
    }

    /**
     * @param id 삭제하는 댓글 고유 id
     * @return boolean 삭제 성공 여부
     */
    @GetMapping("/delete")
    public ResponseEntity<Boolean> delReply(@RequestParam Long id){
        System.out.println("삭제 하는 게시판 넘버 : " + id);
        boolean isTrue = replyService.delReply(id);
        return ResponseEntity.ok(isTrue);
    }

    // 자유 게시글 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<Boolean> replyModify(@PathVariable Long id, @RequestBody ReplyDto replyDto) {
        boolean isTrue = replyService.modifyReply(id, replyDto);
        return ResponseEntity.ok(isTrue);
    }
}
