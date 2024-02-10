package com.kimseungjin.cafe.domain.member.repository;

import com.kimseungjin.cafe.domain.member.entity.Member;
import com.kimseungjin.cafe.global.audit.SoftDeleteRepository;

import java.util.UUID;

public interface MemberRepository extends SoftDeleteRepository<Member, UUID> {}
