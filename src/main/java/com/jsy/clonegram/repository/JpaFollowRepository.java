package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface JpaFollowRepository extends JpaRepository<Follow, Long> {

}
