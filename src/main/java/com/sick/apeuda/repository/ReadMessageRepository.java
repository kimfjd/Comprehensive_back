package com.sick.apeuda.repository;

import com.sick.apeuda.entity.Member;
import com.sick.apeuda.entity.ReadMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ReadMessageRepository extends JpaRepository<ReadMessage, Long> {
    @Query("SELECT rm FROM ReadMessage rm WHERE (rm.member1 = :member1 AND rm.member2 = :member2) OR (rm.member1 = :member2 AND rm.member2 = :member1)")
    List<ReadMessage> findAllByMembers(@Param("member1") Member member1, @Param("member2") Member member2);
}
