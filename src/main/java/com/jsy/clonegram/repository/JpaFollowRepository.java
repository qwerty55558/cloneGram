package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFollowRepository extends JpaRepository<Follow, Long> {

}
