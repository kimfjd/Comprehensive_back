package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Reply;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("SELECT r FROM Reply r WHERE r.board.boardNo = :boardId")
    List<Reply> findRepliesByBoardId(@Param("boardId") Long boardId);


}
