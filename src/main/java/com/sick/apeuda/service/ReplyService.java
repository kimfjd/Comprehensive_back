package com.sick.apeuda.service;

import com.sick.apeuda.dto.BoardReqDto;
import com.sick.apeuda.dto.ReplyDto;
import com.sick.apeuda.entity.Board;
import com.sick.apeuda.entity.Project;
import com.sick.apeuda.entity.Reply;
import com.sick.apeuda.entity.User;
import com.sick.apeuda.repository.BoardRepository;
import com.sick.apeuda.repository.ProjectRepository;
import com.sick.apeuda.repository.ReplyRepository;
import com.sick.apeuda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.sick.apeuda.security.SecurityUtil.getCurrentMemberId;
@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final BoardRepository boardRepository;

    public boolean saveReply(ReplyDto replyDto) {
        try {
            Reply reply = new Reply();
            String memberId = getCurrentMemberId();
            User user = userRepository.findById("testId@gmail.com").orElseThrow(
                    () -> new RuntimeException("User does not exist")
            );
            reply.setReplyId(replyDto.getReplyId());
            reply.setContent(replyDto.getContent());
            reply.setRegDate(LocalDateTime.now());
            if (replyDto.getBoardId() != null) {
                Board board = boardRepository.findById(replyDto.getBoardId()).orElseThrow(
                        () -> new RuntimeException("Board does not exist")
                );
                reply.setBoard(board);
            } else if (replyDto.getProjectId() != null) {
                Project project = projectRepository.findById(replyDto.getProjectId()).orElseThrow(
                        () -> new RuntimeException("Project does not exist")
                );
                reply.setProject(project);
            } else {
                throw new RuntimeException("Either boardId or projectId must be provided");
            }
            reply.setUser(user);
            replyRepository.save(reply);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * @param id 댓글 고유 ID
     * @return 삭제 성공 여부
     */
    public boolean delReply(Long id) {
        try{
            replyRepository.deleteById(id);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 게시글 수정
    public boolean modifyReply(Long id, ReplyDto replyDto) {
        try {
            Reply reply = replyRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Board does not exist")
            );
            reply.setReplyId(replyDto.getReplyId());
            reply.setContent(replyDto.getContent());
            reply.setRegDate(LocalDateTime.now());
            // 프론트랑 연결시 null안되면 0으로 시도해보기
            if (replyDto.getBoardId() != null) {
                Board board = boardRepository.findById(replyDto.getBoardId()).orElseThrow(
                        () -> new RuntimeException("Board does not exist")
                );
                reply.setBoard(board);
            } else if (replyDto.getProjectId() != null) {
                Project project = projectRepository.findById(replyDto.getProjectId()).orElseThrow(
                        () -> new RuntimeException("Project does not exist")
                );
                reply.setProject(project);
            } else {
                throw new RuntimeException("Either boardId or projectId must be provided");
            }
            replyRepository.save(reply);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
