package com.jsy.clonegram.mybatis.mapper;

import com.jsy.clonegram.dao.Follow;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserCreateDto;
import com.jsy.clonegram.dto.UserUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * MyBatis 사용을 위한 userMapper
 */
@Mapper
public interface UserMapper {
    void save(UserCreateDto user);
    Optional<User> findById(@Param("id") Long id);

    void updateUser(UserUpdateDto updateDto);

    Integer delete(@Param("id") Long userId);

    Optional<User> findByName(@Param("username") String username);

    Optional<User> findByEmail(@Param("email") String email);

    List<User> findAll();

    List<Follow> findFollowersByUserId(@Param("id") Long userId);

    List<Follow> findFollowingsByUserId(@Param("id") Long userId);

    void followUser(Follow follow);

    void unfollowUser(Follow follow);
}
