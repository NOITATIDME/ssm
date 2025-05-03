package com.ssm.domain.member.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssm.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberKey(String memberKey);
}