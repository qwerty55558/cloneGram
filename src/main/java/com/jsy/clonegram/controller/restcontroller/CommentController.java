package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public String commentReceive(@RequestParam(name = "comment") String comment, @RequestParam(name = "postId") Long postId){
        commentService.createComment(comment, postId);
        return "redirect:/post?id=" + postId;
    }

}
