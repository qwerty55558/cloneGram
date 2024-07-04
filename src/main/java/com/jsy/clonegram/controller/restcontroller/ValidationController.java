package com.jsy.clonegram.controller.restcontroller;

import com.jsy.clonegram.dto.DefaultMessageDto;
import com.jsy.clonegram.dto.ValidationRequestDto;
import com.jsy.clonegram.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ValidationController {

    private final ValidationService validationService;

    @PostMapping("/validation/id")
    public DefaultMessageDto getValidateUsername(@RequestBody ValidationRequestDto id) {
        return validationService.getValidateUsername(id);
    }

    @PostMapping("/validation/email")
    public DefaultMessageDto getValidateEmail(@RequestBody ValidationRequestDto id) {
        return validationService.getValidateEmail(id);
    }
}
