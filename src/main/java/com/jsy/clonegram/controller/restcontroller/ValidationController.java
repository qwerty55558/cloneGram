package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.DefaultMessageDto;
import com.jsy.clonegram.dto.ValidationRequestDto;
import com.jsy.clonegram.repository.UserRepository;
import com.jsy.clonegram.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ValidationController {

    private final UserRepository repository;
    private final RedisService service;

    @PostMapping("/validation/id")
    public DefaultMessageDto getValidateUsername(@RequestBody ValidationRequestDto id) {
        Optional<User> byName = repository.findByName(id.getId());
        if (byName.isPresent()) {
            service.setDuplicated(id.getId());
            return new DefaultMessageDto("fail");
        }else {
            service.setDuplicationCheckId(id.getId());
            return new DefaultMessageDto("success");
        }
    }
}
