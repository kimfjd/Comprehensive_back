package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Board;
import com.sick.apeuda.entity.Skill;
import com.sick.apeuda.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시판 정보 생성")
    public void createBoardContent(){
        Board board = new Board();
        board.setContent("나는 개똥벌레 친구가 없네");
        board.setTitle("성진이의 일기");
        board.setImgPath("./image");
        board.setRegDate(LocalDateTime.now());
        boardRepository.save(board);
    }

    @Test
    @DisplayName("게시판 리스트 생성")
    public void createBoardList() {
        for(int i = 1; i <= 10; i++) {
            Board board = new Board();
            board.setContent(i + "번 나는 개똥벌레 친구가 없네");
            board.setTitle(i + "번 성진이의 일기");
            board.setImgPath("./image");
            board.setRegDate(LocalDateTime.now());
            boardRepository.save(board);
        }
    }

    @Test
    @DisplayName("자유 게시판 리스트 불러오기")
    @Transactional
    public void findAllBoardTest(){
        this.createBoardList();
        List<Board> boardList = boardRepository.findAll();
        for (Board e : boardList) System.out.println(e.toString());
    }

}
