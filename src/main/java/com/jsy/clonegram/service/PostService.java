package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository rep;
}
