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

    /**
     * 게시판 전체 조회 메소드
     * @return boardDtos Board 엔티티타입의 List 반환
     */
    public List<BoardDto> getBoardList() {
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boards) {
            boardDtos.add(convertEntityToDto(board));
        }
        return boardDtos;
    }

    /**
     * 게시글 상세 조회
     * @param id 게시판 고유번호
     * @return param으로 받은 id에 해당하는 BoardDto객체 값 반환
     */
    public BoardDto getBoardDetail(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("해당 게시글이 존재하지 않습니다.")
        );
        return convertEntityToDetailDto(board);
    }

    public boolean delboard(Long id) {
        try{
            boardRepository.deleteById(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    // *** 이밑으론 DTO변환 메소드들 ***


    /**
     * 게시글 엔티티를 DTO로 변환(자유 게시글 전체 조회)
     * @param board Board 엔티티 타입
     * @return boardDto -> 게시판 전체 리스트 반환
     */
    private BoardDto convertEntityToDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setTitle(board.getTitle());
        boardDto.setNickName(board.getUser().getNickname());
        boardDto.setProfile_img(board.getUser().getProfileImgPath());
        //boardDto.setReplies(board.getReply().ge);
        boardDto.setRegDate(board.getRegDate());
        //김기주 이메일 가져오는 거 수정
        boardDto.setEmail(board.getUser().getEmail());
        return boardDto;
    }
    // board와 reply를 다른 서비스에서 관리 해야되는거면 수정해야됨
    /**
     * 게시글 엔티티를 BoardDto로 변환(자유 게시글 상세페이지, 댓글 포함)
     * @param board Board 엔티티 객체
     * @return BoardDto -> 게시판 상세 내용과 해당 게시판의 댓글 리스트 반환
     */
    private BoardDto convertEntityToDetailDto(Board board) {
        BoardDto boardDetailDto = new BoardDto();
        boardDetailDto.setBoardId(board.getBoardId());
        boardDetailDto.setTitle(board.getTitle());
        boardDetailDto.setContent(board.getContent());
        boardDetailDto.setImg(board.getImgPath());
        boardDetailDto.setNickName(board.getUser().getNickname());
        boardDetailDto.setProfile_img(board.getUser().getProfileImgPath());
        boardDetailDto.setRegDate(board.getRegDate());


        // 댓글 목록
        List<Reply> replies = replyRepository.findByBoardId(board.getBoardId());
        List<ReplyDto> replyDtos = new ArrayList<>();
        for (Reply reply : replies) {
            replyDtos.add(convertEntityToReplyDto(reply, board.getBoardId()));
        }
        boardDetailDto.setReplies(replyDtos);
        return boardDetailDto;
    }
    
    /**
     * 댓글 엔티티를 DTO로 변환
     * @param reply Reply 엔티티 객체
     * @param boardId 댓글이 들어가있는 게시판 고유 번호
     * @return ReplyDto -> 게시판 번호에 맞는 댓글 리스트
     */
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
