package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dao.Likey;
import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.repository.JpaLikeyRepository;
import com.jsy.clonegram.repository.PostRepository;
import com.jsy.clonegram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostRestController {

    private final PostRepository postRepository;
    private final JpaLikeyRepository jpaLikeyRepository;
    private final UserService userService;

    @GetMapping("find/posts")
    public List<Post> getRandPost() {
        return postRepository.findWithPagination(12);
    }

    @PostMapping("find/posts")
    public Page<Post> getCondPost(@RequestParam("cond") String cond, @RequestParam("pageNum") Integer pageNum) {
        PageRequest reqPage = PageRequest.of(pageNum, 12, Sort.by(Sort.Direction.DESC,"id"));
        return postRepository.findPosts(reqPage, cond);
    }

    @PostMapping("find/likey/info")
    public boolean getLikeyStat(@RequestParam("postname") Long postName) {
        Long userIdOnSession = userService.getUserIdOnSession();
        return jpaLikeyRepository.findByUserIdAndPostId(userIdOnSession, postName).isPresent();
    }
    
    @PostMapping("find/likey")
    public String setLikey(@RequestParam("postname") Long postName,@RequestParam("action") String req) {
        Likey likey = createLikey(postName);

        if (req.equals("O")) {
            jpaLikeyRepository.save(likey);
        }else {
            jpaLikeyRepository.deleteByUserIdAndPostId(likey.getUserId(),likey.getPostId());
        }

        return likey.toString();
    }

    private Likey createLikey(Long postName) {
        Long userIdOnSession = userService.getUserIdOnSession();
        Likey likey = new Likey();
        likey.setPostId(postName);
        likey.setUserId(userIdOnSession);

        return likey;
    }
}
