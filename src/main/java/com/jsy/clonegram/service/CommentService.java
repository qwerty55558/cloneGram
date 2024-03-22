package com.jsy.clonegram.service;

import com.jsy.clonegram.repository.JpaCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final JpaCommentRepository jpaCommentRepository;

}
