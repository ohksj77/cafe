package com.kimseungjin.cafe.support.repository;

import com.kimseungjin.cafe.config.encrypt.EncryptConfig;
import com.kimseungjin.cafe.support.database.DatabaseSweepTest;
import com.kimseungjin.cafe.support.database.FlywayDisableTest;
import com.kimseungjin.cafe.utils.EncryptUtils;

import org.springframework.context.annotation.Import;

@Import({EncryptConfig.class, EncryptUtils.class})
@EnableDataJpa
@DatabaseSweepTest
public abstract class RepositoryTest extends FlywayDisableTest {}
