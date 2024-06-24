package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Board;
import com.sick.apeuda.entity.Reply;
import com.sick.apeuda.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private UserRepository userRepository;
    // 회원 엔티티 생성
//    public User createMemberInfo() {
//        User user = new User();
//        user.setEmail("testId@gmail.com");
//        user.setPassword("1234");
//        user.setName("박성진");
//        user.setEmail("jks2024@gmail.com");
//        user.setMyInfo("술쟁이 입니다.");
//        user.setNickname("개똥벌레");
//        user.setIdentityNumber("941231");
//        user.setProfileImgPath("");
//        //user.setRegDate(LocalDateTime.now());
//        return user;
//    }
    // 게시판 엔티티 생성
    public Board createBoardContentEntity(){
        Board board = new Board();
        board.setBoardId(1L);
        board.setContent("나는 개똥벌레 친구가 없네");
        board.setTitle("성진이의 일기");
        board.setImgPath("./image");
        board.setRegDate(LocalDateTime.now());
        return board;
    }
    
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
            board.setBoardId(1L);
            board.setContent(i + "번 나는 개똥벌레 친구가 없네");
            board.setTitle(i + "번 성진이의 일기");
            board.setImgPath("./image");
            board.setRegDate(LocalDateTime.now());
            boardRepository.save(board);
        }
    }
    
//    @Test
//    @DisplayName("댓글 리스트 생성")
//    public void createReplyList() {
//        User user = createMemberInfo();
//        Board board = createBoardContentEntity();
//        for(int i = 1; i <= 5; i++) {
//            Reply reply = new Reply();
//            reply.setContent(1 + "번째 댓글");
//            reply.setRegDate(LocalDateTime.now());
//            System.out.println("여기 : " + board);
//            System.out.println("여기2 : " + reply);
////            reply.setBoard(board.getBoardId());
////            reply.setUser(user);
//            replyRepository.save(reply);
//        }
//    }
//@Test
//@Transactional
//public void testRetrieveRepliesForBoard() {
//    // Create a user
//    User user = new User();
//    user.setEmail("dla@naver.com");
//    user.setPassword("wjdgn");
//    user.setName("임정후");
//    user.setNickname("dla");
//    user.setIdentityNumber("940502");
//    user.setMyInfo("테스트를위해 생성한 아이디");
//    this.userRepository.save(user);
//
//    // Create a board
//    Board board = new Board();
//    board.setTitle("Test Board");
//    board.setContent("This is a test board.");
//    board.setUser(user);
//    board.setRegDate(LocalDateTime.now());
//    this.boardRepository.save(board);
//
//    // Verify board is saved
//    Assertions.assertNotNull(board.getBoardId(), "Board ID should not be null after save");
//
//    // Create replies for the board
//    Reply reply1 = new Reply();
//    reply1.setContent("Reply 1 content");
//    reply1.setRegDate(LocalDateTime.now());
//    reply1.setUser(user);
//    reply1.setBoard(board);
//    this.replyRepository.save(reply1);
//
//    Reply reply2 = new Reply();
//    reply2.setContent("Reply 2 content");
//    reply2.setRegDate(LocalDateTime.now());
//    reply2.setUser(user);
//    reply2.setBoard(board);
//    this.replyRepository.save(reply2);
//
//    // Retrieve board from the database
//    Optional<Board> optionalBoard = boardRepository.findById(board.getBoardId());
//    Assertions.assertTrue(optionalBoard.isPresent(), "Board should be present in the database");
//    Board savedBoard = optionalBoard.get();
//
//    // Check if savedBoard is not null
//    Assertions.assertNotNull(savedBoard, "Saved board should not be null");
//
//    // Retrieve replies and handle if null
//    List<Reply> replies = (List<Reply>) savedBoard.getReply();
//
//    System.out.println("힘ㄷ르어 : " + replies);
//    System.out.println("힘ㄷ르어2 : " + savedBoard.getReply());
//    System.out.println("힘ㄷ르어3 : " + savedBoard);
//    if (replies == null) {
//        // Handle case where replies list is null
//        Assertions.fail("Replies list should not be null");
//    } else {
//        // Assert the number of replies
//        Assertions.assertEquals(2, replies.size(), "Expected 2 replies");
//
//        // Print or further assertions can be made based on the retrieved replies
//        for (Reply reply : replies) {
//            System.out.println(reply);
//        }
//    }
//}


    @Test
    @DisplayName("자유 게시판 리스트 불러오기")
    @Transactional
    public void findAllBoardTest(){
        this.createBoardList();
        List<Board> boardList = boardRepository.findAll();
        for (Board e : boardList) System.out.println(e.toString());
    }


}
