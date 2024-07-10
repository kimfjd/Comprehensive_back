package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Apply;
import com.sick.apeuda.entity.Board;
import com.sick.apeuda.entity.Member;
import com.sick.apeuda.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
//    List<Apply> findByProjectIdAndMemberIdAndManagerId(Long projectId,Member applicant ,Member manager);
    Optional<Apply> findByApplyId(Long id);
    boolean existsByMemberAndProject(Member member, Project project);
    List<Apply> findByManagerAndApplyStatus(Member manager, boolean applyStatus);
//    @Query("SELECT a FROM Apply a WHERE a.project.id = :projectId")
//    List<Apply> findByProjectId(@Param("projectId") Long projectId);
}
