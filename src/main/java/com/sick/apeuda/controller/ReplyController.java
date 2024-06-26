package com.sick.apeuda.controller;

import com.sick.apeuda.dto.BoardReqDto;
import com.sick.apeuda.dto.ReplyDto;
import com.sick.apeuda.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Boolean> deleteReply(@RequestParam Long id){
        System.out.println("삭제 하는 게시판 넘버 : " + id);
        boolean isTrue = replyService.deleteReply(id);
        return ResponseEntity.ok(isTrue);
    }

    // 댓글 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<Boolean> replyModify(@PathVariable Long id, @RequestBody ReplyDto replyDto) {
        boolean isTrue = replyService.modifyReply(id, replyDto);
        return ResponseEntity.ok(isTrue);
    }
    
    // 자유 게시판 댓글 페이징
    @GetMapping("/board-reply/{boardId}")
    public ResponseEntity<List<ReplyDto>> boardReplyPageList(@PathVariable Long boardId,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        List<ReplyDto> list = replyService.getBoardReplyList(boardId, page, size);
        return ResponseEntity.ok(list);
    }

    // 플젝 게시판 댓글 페이징
    @GetMapping("/project-reply/{projectId}")
    public ResponseEntity<List<ReplyDto>> projectReplyPageList(@PathVariable Long projectId,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        List<ReplyDto> list = replyService.getProjectReplyList(projectId, page, size);
        return ResponseEntity.ok(list);
    }
}

// 자유 게시판 댓글 페이징 -> 한 컨트롤러에서 자유,플젝을 분기시키고 싶었는데 방법이 안떠오름
//    @GetMapping({"/page/{boardId}", "/page/{projectId}"})
//    public ResponseEntity<List<ReplyDto>> replyPageList(@PathVariable Long boardId,
//                                                        @PathVariable Long projectId,
//                                                        @RequestParam(defaultValue = "1") int page,
//                                                       @RequestParam(defaultValue = "5") int size) {
//        List<ReplyDto> list = replyService.getReplyList(boardId, projectId, page, size);
//        return ResponseEntity.ok(list);
//    }
    // 플젝 게시판 댓글 페이징
//}
