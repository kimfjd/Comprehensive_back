package com.sick.apeuda.service;

import com.sick.apeuda.dto.BoardDto;
import com.sick.apeuda.dto.ReplyDto;
import com.sick.apeuda.entity.Board;
import com.sick.apeuda.entity.Reply;
import com.sick.apeuda.repository.BoardRepository;
import com.sick.apeuda.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    // 게시글 전체 조회
    public List<BoardDto> getBoardList() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boards) {
            boardDtos.add(convertEntityToDto(board));
        }
        return boardDtos;
    }
    // 게시글 엔티티를 DTO로 변환
    private BoardDto convertEntityToDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        boardDto.setImg(board.getImgPath());
        boardDto.setNickName(board.getUser().getNickname());
        boardDto.setProfile_img(board.getUser().getProfileImgPath());
        //boardDto.setReplies(board.getReply().ge);
        boardDto.setRegDate(board.getRegDate());
        return boardDto;
    }
    // 게시글 상세 조회
    public BoardDto getBoardDetail(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );
        return convertEntityToDetailDto(board);
    }


    // 댓글 목록 조회
    public List<ReplyDto> getReplyList(Long boardId) {
        try {
            Board board = boardRepository.findById(boardId).orElseThrow(
                    () -> new RuntimeException("해당 댓글이 존재하지 않습니다.")
            );
            List<Reply> replies = replyRepository.findRepliesByBoardId(board.getBoardId());
            List<ReplyDto> replyDtos = new ArrayList<>();
            for (Reply reply : replies) {
                replyDtos.add(convertEntityToReplyDto(reply,boardId));
            }
            return replyDtos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 게시글 엔티티를 BoardDetailDto로 변환
    private BoardDto convertEntityToDetailDto(Board board) {
        BoardDto boardDetailDto = new BoardDto();
        boardDetailDto.setBoardId(board.getBoardId());
        boardDetailDto.setTitle(board.getTitle());
        boardDetailDto.setContent(board.getContent());
        boardDetailDto.setImg(board.getImgPath());
        boardDetailDto.setNickName(board.getUser().getNickname());
        boardDetailDto.setProfile_img(board.getUser().getProfileImgPath());
        boardDetailDto.setRegDate(board.getRegDate());

        // 댓글 목록 추가
        List<Reply> replies = replyRepository.findRepliesByBoardId(board.getBoardId());
        List<ReplyDto> replyDtos = new ArrayList<>();
        for (Reply reply : replies) {
            replyDtos.add(convertEntityToReplyDto(reply, board.getBoardId()));
        }
        boardDetailDto.setReplies(replyDtos);

        return boardDetailDto;
    }

    // 댓글 엔티티를 DTO로 변환
    private ReplyDto convertEntityToReplyDto(Reply reply, Long boardId) {
        ReplyDto replyDto = new ReplyDto();
        replyDto.setBoardId(boardId);
        replyDto.setContent(reply.getContent());
        replyDto.setProfile_img(reply.getUser().getProfileImgPath());
        replyDto.setNickName(reply.getUser().getNickname());
        replyDto.setRegDate(reply.getRegDate());
        return replyDto;
    }
}
