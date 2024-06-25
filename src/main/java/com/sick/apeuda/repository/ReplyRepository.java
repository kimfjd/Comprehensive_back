package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Reply;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("SELECT r FROM Reply r WHERE r.board.id = :boardId")
    List<Reply> findByBoardId(@Param("boardId") Long boardId);

    @Query("SELECT r FROM Reply r WHERE r.project.id = :projectId")
    List<Reply> findByProjectId(@Param("projectId") Long projectId);

}
