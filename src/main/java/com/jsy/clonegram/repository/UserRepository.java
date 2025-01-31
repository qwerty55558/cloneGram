package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Follow;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserCreateDto;
import com.jsy.clonegram.dto.UserUpdateDto;

import java.util.List;
import java.util.Optional;

/**
 * User단에서 필요한 데이터를 REpository와 이어주는 인터페이스
 */
public interface UserRepository {
    void save(UserCreateDto user);

    Optional<User> findById(Long id);

    void update(UserUpdateDto updateDto);

    Integer delete(Long userId);

    Optional<User> findByName(String username);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    List<Follow> findFollowersByUserId(Long userId);

    List<Follow> findFollowingsByUserId(Long userId);

    void followUser(Follow follow);

    void unfollowUser(Follow follow);

}
