package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Grade;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserCreateDto;
import com.jsy.clonegram.dto.UserUpdateDto;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * User의 비즈니스 로직을 담음
 * createUser 는 user의 password의 encoding 과정이 필요하여 encode를 비밀번호로 만듦.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    @Transactional
    public Boolean createUser(UserCreateDto user){

        Optional<User> byName = userRepository.findByName(user.getUserName());
        if (byName.isEmpty()) {
            String encode = encoder.encode(user.getPassword());
            user.setPassword(encode);
            user.setGrade(Grade.Bronze);

            userRepository.save(user);

            return true;
        }

        return false;
    }

    @Transactional
    public Boolean updateUser(User user, UserUpdateDto dto) {
        dto.setTargetId(user.getId());
        userRepository.update(dto);
        return true;
    }

    @Transactional
    public Boolean updateUser(Long userId, UserUpdateDto dto){
        dto.setTargetId(userId);
        userRepository.update(dto);
        return true;
    }

    public String getUsernameOnSession(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUsername();
    }

    @Transactional
    public Long getUserIdOnSession(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        Optional<User> byName = userRepository.findByName(username);
        if (byName.isPresent()) {
            return byName.get().getId();
        }
        return -1L;
    }

    @Transactional
    public Long getUserIdByName(String userName) {
        Optional<User> byName = userRepository.findByName(userName);
        return byName.map(User::getId).orElse(null);
    }

    @Transactional
    public User getUserBySession(){
        Optional<User> byId = userRepository.findById(getUserIdOnSession());
        return byId.orElse(null);
    }

    @Transactional
    public String getUserNameByUserId(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        return byId.map(User::getUserName).orElse(null);
    }

    @Transactional
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByName(userName);
    }

    @Transactional
    public User getUserById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
