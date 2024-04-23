package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Comment;
import com.jsy.clonegram.dao.Follow;
import com.jsy.clonegram.dao.Likey;
import com.jsy.clonegram.dao.Message;
import com.jsy.clonegram.repository.JpaCommentRepository;
import com.jsy.clonegram.repository.JpaFollowRepository;
import com.jsy.clonegram.repository.JpaLikeyRepository;
import com.jsy.clonegram.repository.JpaMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PageReturnService {
    private final JpaCommentRepository commentRepository;
    private final JpaFollowRepository followRepository;
    private final JpaLikeyRepository likeyRepository;
    private final JpaMessageRepository messageRepository;

    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElse(null);
    }
    public Follow getFollow(Long id) {
        return followRepository.findById(id).orElse(null);
    }
    public Likey getLikey(Long id) {
        return likeyRepository.findById(id).orElse(null);
    }
    public Message getMessage(Long id) {
        return messageRepository.findById(id).orElse(null);
    }
}

