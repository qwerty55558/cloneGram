package com.jsy.clonegram.service;

import com.jsy.clonegram.repository.JpaLikeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeyService {
    private final JpaLikeyRepository likeyRepository;

    public Optional<Long> getLikeyByPostId(Long postId) {
        return likeyRepository.countByPostId(postId);
    }
}
