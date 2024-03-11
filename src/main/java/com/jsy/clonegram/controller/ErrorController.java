package com.jsy.clonegram.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * AccessRejectedHandler에서 보낸 get 요청을 받는 페이지 (request setattribute 된 데이터들과 함께 주소로 보내기 때문에
 * 따로 작성할 필요가 없이 그냥 html 값만 리턴해줌)
 */

@Controller
@Slf4j
@RequiredArgsConstructor
public class ErrorController {

    @GetMapping("error/redirect")
    public String errorredirect(){
        return "/error/redirect/adminpagerejected";
    }
}
