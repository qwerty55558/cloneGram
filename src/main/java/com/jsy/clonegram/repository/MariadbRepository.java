package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserUpdateDto;
import com.jsy.clonegram.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

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
    public User update(User user, UserUpdateDto updateDto) {
        return userMapper.updateUser(user, updateDto);
    }

    @Override
    public Integer delete(Long userId) {
        return userMapper.delete(userId);
    }

    @Override
    public Optional<User> findByName(String username) {
        return userMapper.findByName(username);
    }
}
