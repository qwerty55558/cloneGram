package com.jsy.clonegram.controller;

import com.jsy.clonegram.dao.Constants;
import com.jsy.clonegram.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * home으로 get 요청이 오면 `home` 페이지를 보여준다.
 * Securitycontextholer에서 인증정보를 불러와서 username을 받아옴 이를 추후에
 * findbyname 메소드와 연동해 다른 정보들을 출력해줄 수 있다.
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final FollowService followService;
    private final PostService postService;
    private final UserService userService;
    private final UserPictureService userPictureService;

    @Value("${VIDEO.URL}")
    private String videoUrl;

    @Value("${FILE.MAXSIZE}")
    private Integer fileMaxSize;

    @Value("${POST.CAPTIONSIZE}")
    private Integer postCaptionSize;

    @GetMapping({"/", "/home"})
    public String homeController(Model model) {

        model.addAttribute("follow", followService.getFollowSet());
        model.addAttribute("user", userService.getUserBySession());
        model.addAttribute("picurl", userPictureService.getPicUrlDto());
        model.addAttribute("posts", postService.getPosts());
        model.addAttribute("videoPath", videoUrl);
        model.addAttribute("fileMaxSize", fileMaxSize * Constants.FILE_MAX_SIZE);
        model.addAttribute("postCaptionSize", postCaptionSize);

        return "home";
    }


}
