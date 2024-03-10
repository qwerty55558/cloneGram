package com.jsy.clonegram.repository;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserUpdateDto;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * User단에서 필요한 데이터를 REpository와 이어주는 인터페이스
 */
public interface UserRepository {
    void save(User user);

    Optional<User> findById(Long id);

    User update(User user, UserUpdateDto updateDto);

    Integer delete(Long userId);

    Optional<User> findByName(String username);
}
