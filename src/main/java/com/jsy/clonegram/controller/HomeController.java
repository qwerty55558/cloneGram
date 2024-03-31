package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.FollowSetDto;
import com.jsy.clonegram.dto.PicUrlDto;
import com.jsy.clonegram.repository.PostRepository;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.FollowService;
import com.jsy.clonegram.service.UserPictureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * home으로 get 요청이 오면 `home` 페이지를 보여준다.
 * Securitycontextholer에서 인증정보를 불러와서 username을 받아옴 이를 추후에
 * findbyname 메소드와 연동해 다른 정보들을 출력해줄 수 있다.
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;
    private final UserPictureService pic;
    private final PostRepository postRepository;
    private final FollowService followService;

    @GetMapping({"/","/home"})
    public String homeController(Model model) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        Optional<User> byName = userRepository.findByName(username);
        if (byName.isPresent()) {
            List<Post> postsById = postRepository.findPostsById(byName.get().getId());

            PicUrlDto picUrlDto = new PicUrlDto();
            picUrlDto.setMiniPic(pic.getMiniPicUrl());
            picUrlDto.setProfilePic(pic.getProfilePicUrl());

            FollowSetDto followSetDto = new FollowSetDto();

            followSetDto.setFollower(followService.getFollowerCount(byName.get().getId()));
            followSetDto.setFollowing(followService.getFollowingCount(byName.get().getId()));

            log.info("Follow = {}",followSetDto.toString());

            model.addAttribute("follow", followSetDto);
            model.addAttribute("user", byName.get());
            model.addAttribute("picurl", picUrlDto);
            model.addAttribute("posts", postsById);
            return "home";
        }

        return "redirect:/login";
    }


}
