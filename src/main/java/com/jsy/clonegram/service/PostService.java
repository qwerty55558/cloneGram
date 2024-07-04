package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Comment;
import com.jsy.clonegram.dao.Likey;
import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dto.HomePostDto;
import com.jsy.clonegram.repository.JpaLikeyRepository;
import com.jsy.clonegram.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final JpaLikeyRepository jpaLikeyRepository;
    private final UserService userService;
    private final FollowService followService;
    private final LikeyService likeyService;
    private final CommentService commentService;
    private final UserPictureService pictureService;

    @Transactional
    public Map<Long, String> getPicByPost(Post post){
        Map<Long, String> pic = new HashMap<>();
        Long userId = post.getUserId();
        pic.put(userId, pictureService.getMidSizeProfileById(userId));
        return pic;
    }

    @Transactional
    public Map<Long, String> getUsernameByPost(Post post){
        Map<Long, String> username = new HashMap<>();
        Long userId = post.getUserId();
        username.put(userId, userService.getUserNameByUserId(userId));
        return username;
    }

    @Transactional
    public List<Post> randPost(Integer size){
        return postRepository.findWithPagination(size);
    }

    @Transactional
    public Page<Post> getCondPost(String cond, Integer pageNum, Integer pageSize){
        PageRequest reqPage = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        return postRepository.findPosts(reqPage, cond);
    }

    @Transactional
    public boolean getLikeyStat(Long postName){
        Long userIdOnSession = userService.getUserIdOnSession();
        return jpaLikeyRepository.findByUserIdAndPostId(userIdOnSession, postName).isPresent();
    }

    @Transactional
    public String setLikey(Long postId, String req){
        Likey likey = createLikey(postId);
        if (req.equals("O")){
            jpaLikeyRepository.save(likey);
        }else {
            jpaLikeyRepository.deleteByUserIdAndPostId(likey.getUserId(), likey.getPostId());
        }
        return likey.toString();
    }

    @Transactional
    public List<Post> getPosts(){
        return postRepository.findPostsById(userService.getUserIdOnSession());
    }

    @Transactional
    public Post getPost(Long postId){
        return postRepository.findById(postId).orElse(null);
    }

    @Transactional
    public List<Post> getPostsByUserId(Long userId){
        return postRepository.findPostsById(userId);
    }

    @Transactional
    public Page<HomePostDto> getRecommendedPosts(Integer size, Integer pageNum){
        try {
            LinkedList<HomePostDto> posts = new LinkedList<>();
            followService.getMyFollowing().forEach(c -> {
                postRepository.findPostsById(c.getUserId()).forEach(p -> {
                    HomePostDto homePostDto = new HomePostDto();
                    homePostDto.setCreatorName(userService.getUserNameByUserId(c.getUserId()));
                    homePostDto.setCreatorPic(c.getUserPic());
                    homePostDto.setPostPic(p.getImageUrl());
                    if (p.getCaption() == null || p.getCaption().equals("")) {
                        homePostDto.setCaption(" ");
                    }else {
                        homePostDto.setCaption(p.getCaption());
                    }
                    homePostDto.setPostId(p.getId());
                    if (commentService.getRandComment(p.getId()) != null) {
                        homePostDto.setRandComment(commentService.getRandComment(p.getId()));
                    }else {
                        homePostDto.setRandComment(new Comment());
                    }

                    Optional<Long> likeyByPostId = likeyService.getLikeyByPostId(p.getId());
                    if (likeyByPostId.isPresent()){
                        homePostDto.setLikeyCount(likeyByPostId.get());
                    }else {
                        homePostDto.setLikeyCount(0L);
                    }
                    posts.add(homePostDto);
                });
            });

            Comparator<HomePostDto> byPostIdDesc = Comparator.comparing(HomePostDto::getPostId).reversed();
            posts.sort(byPostIdDesc);

            Pageable pageable = PageRequest.of(pageNum, size);

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), posts.size());
            List<HomePostDto> pageContent = posts.subList(start, end);
            return new PageImpl<>(pageContent, pageable, posts.size());
        }catch (Exception e){
            log.info("인덱스 넘어간 요청");
            return null;
        }
    }

    private Likey createLikey(Long postName) {

        Long userIdOnSession = userService.getUserIdOnSession();
        Likey likey = new Likey();
        likey.setPostId(postName);
        likey.setUserId(userIdOnSession);

        return likey;
    }

    @Transactional
    public boolean updatePost(Long postId, MultipartFile file, String caption) {

        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Post targetPost = post.get();
            if (caption != null){
                targetPost.setCaption(caption);
            }
            if (file != null) {
                pictureService.updatePostPicture(file, postId);
                targetPost.setImageUrl(pictureService.getPostSizePicById(postId));
            }
            postRepository.save(targetPost);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deletePost(Long postId) {
        try {
            postRepository.delete(postId);
            pictureService.deletePostPicture(postId);
            return true;
        }catch (Exception e){
            log.info("Delete Post Error :", e);
            return false;
        }
    }

}
