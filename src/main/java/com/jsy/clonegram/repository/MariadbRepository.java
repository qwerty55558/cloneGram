package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.Grade;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserUpdateDto;
import com.jsy.clonegram.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * UserRepsitory를 상속받아 다형성을 유지함
 * 하지만 MyBatis의 UserMapper에 종속되어있음
 */
@Slf4j
@RequiredArgsConstructor
public class MariadbRepository implements UserRepository {

    private final UserMapper userMapper;

    @Override
    public void save(User user) {
        userMapper.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public void update(UserUpdateDto updateDto) {
        userMapper.updateUser(updateDto);
    }

    @Override
    public Integer delete(Long userId) {
        return userMapper.delete(userId);
    }

    @Override
    public Optional<User> findByName(String username) {
        return userMapper.findByName(username);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
