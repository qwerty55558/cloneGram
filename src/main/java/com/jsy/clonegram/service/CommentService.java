package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Comment;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.repository.JpaCommentRepository;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final JpaCommentRepository commentRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(String comment, Long postId) {
        String usernameOnSession = userService.getUsernameOnSession();
        Optional<User> byName = userRepository.findByName(usernameOnSession);
        if (byName.isPresent()){
            Comment commentForm = new Comment();
            commentForm.setContent(comment);
            commentForm.setPostId(postId);
            commentForm.setUserId(byName.get().getId());

            commentRepository.save(commentForm);
        }
    }

    @Transactional
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findCommentsByPostId(postId);
    }

    @Transactional
    public Comment getRandComment(Long postId){
        List<Comment> commentsByPostId = commentRepository.findCommentsByPostId(postId);
        if (commentsByPostId.isEmpty()){
            return null;
        }
        Collections.shuffle(commentsByPostId);
        return commentsByPostId.getFirst();
    }
}
