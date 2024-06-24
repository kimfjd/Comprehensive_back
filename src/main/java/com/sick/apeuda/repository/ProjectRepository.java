package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Board;
import com.sick.apeuda.entity.Project;
import com.sick.apeuda.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p WHERE p.user.email = :userId")
    Optional<Project> findProjectsByUserId(@Param("userId") String userId);

//    @Query("SELECT s FROM Skill s WHERE s.project.id = :projectId")
//    Optional<Skill> findSkillsByProjectId(@Param("projectId") Long projectId);

}
