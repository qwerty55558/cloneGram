package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Follow;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserCreateDto;
import com.jsy.clonegram.dto.UserUpdateDto;
import com.jsy.clonegram.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * UserRepsitory를 상속받아 다형성을 유지함
 * 하지만 MyBatis의 UserMapper에 종속되어있음
 */
@Slf4j
@RequiredArgsConstructor
@EnableTransactionManagement
@Transactional(readOnly = true)
public class MariadbRepository implements UserRepository {

    private final UserMapper userMapper;

    @Transactional
    @Override
    public void unfollowUser(Follow follow) {
        userMapper.unfollowUser(follow);
    }

    @Transactional
    @Override
    public void save(UserCreateDto user) {
        userMapper.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userMapper.findById(id);
    }

    @Transactional
    @Override
    public void update(UserUpdateDto updateDto) {
        userMapper.updateUser(updateDto);
    }

    @Transactional
    @Override
    public Integer delete(Long userId) {
        return userMapper.delete(userId);
    }

    @Override
    public Optional<User> findByName(String username) {
        return userMapper.findByName(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }


    @Transactional
    @Override
    public void followUser(Follow follow) {
        userMapper.followUser(follow);
    }

    @Override
    public List<Follow> findFollowersByUserId(Long userId) {
        return userMapper.findFollowersByUserId(userId);
    }

    @Override
    public List<Follow> findFollowingsByUserId(Long userId) {
        return userMapper.findFollowingsByUserId(userId);
    }
}
