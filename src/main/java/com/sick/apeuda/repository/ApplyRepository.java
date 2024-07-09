package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Apply;
import com.sick.apeuda.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    @Query("SELECT a FROM Apply a WHERE a.project.id = :projectId")
    List<Apply> findByProjectId(@Param("projectId") Long projectId);

}
