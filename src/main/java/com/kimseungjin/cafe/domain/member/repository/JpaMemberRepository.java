package com.kimseungjin.cafe.domain.member.repository;

import com.kimseungjin.cafe.domain.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMemberRepository extends JpaRepository<Member, UUID>, MemberRepository {}
