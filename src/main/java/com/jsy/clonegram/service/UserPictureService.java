package com.jsy.clonegram.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.jsy.clonegram.dao.Post;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.UserUpdateDto;
import com.jsy.clonegram.repository.JpaPostRepository;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPictureService {

    private final Cloudinary cloudinary;
    private final UserService userService;
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final JpaPostRepository jpaPostRepository;

    public String getMiniPicUrl(){
        String usernameOnSession = userService.getUsernameOnSession();
        String image = cloudinary.url().version(redisService.getVersion(usernameOnSession)).forceVersion(true)
                .transformation(new Transformation()
                .width("25")
                .height("25")
                .quality("80"))
                .generate("images/" + usernameOnSession + "/" + usernameOnSession + "_pic");
        // force_version이 적용 안 됨
//        log.info("img = {}",image);
        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setProfileImageUrl(image);
        userService.updateUser(userService.getUserIdOnSession(), userUpdateDto);
        return image;
    }

    public String getMiniPicUrl(String userName){
        String image = cloudinary.url().version(redisService.getVersion(userName)).forceVersion(true)
                .transformation(new Transformation()
                        .width("25")
                        .height("25")
                        .quality("80"))
                .generate("images/" + userName + "/" + userName + "_pic");
        // force_version이 적용 안 됨
//        log.info("img = {}",image);
        return image;
    }

    public String getProfilePicUrl(){
        String usernameOnSession = userService.getUsernameOnSession();
        String image = cloudinary.url().version(redisService.getVersion(usernameOnSession)).forceVersion(true)
                .transformation(new Transformation()
                        .width("200")
                        .height("200"))
                        .generate("images/" + usernameOnSession + "/" + usernameOnSession + "_pic");

//        log.info("img = {}",image);
        return image;
    }

    public String getProfilePicUrl(String userName){
        String image = cloudinary.url().version(redisService.getVersion(userName)).forceVersion(true)
                .transformation(new Transformation()
                        .width("200")
                        .height("200"))
                .generate("images/" + userName + "/" + userName + "_pic");

//        log.info("img = {}",image);
        return image;
    }

    public String getProfilePicUrlHighQ(String userName){
        String image = cloudinary.url().version(redisService.getVersion(userName)).forceVersion(true)
                .transformation(new Transformation()
                        .width("400")
                        .height("400"))
                .generate("images/" + userName + "/" + userName + "_pic");

//        log.info("img = {}",image);
        return image;
    }

    public String getProfileByName(String userName){
        return cloudinary.url().version(redisService.getVersion(userName)).forceVersion(true)
                .transformation(new Transformation()
                        .width("30")
                        .height("30"))
                        .generate("images/" + userName + "/" + userName + "_pic");
    }
    public String getProfileById(Long userId){
        Optional<User> byId = userRepository.findById(userId);
        String userName = byId.get().getUserName();
        return cloudinary.url().version(redisService.getVersion(userName)).forceVersion(true)
                .transformation(new Transformation()
                        .width("30")
                        .height("30"))
                .generate("images/" + userName + "/" + userName + "_pic");
    }

    public String getMidSizeProfileById(Long userId){
        Optional<User> byId = userRepository.findById(userId);
        String userName = byId.get().getUserName();
        return cloudinary.url().version(redisService.getVersion(userName)).forceVersion(true)
                .transformation(new Transformation()
                        .width("80")
                        .height("80"))
                .generate("images/" + userName + "/" + userName + "_pic");
    }

    public String getPostSizePicById(Long postId){
        Long userIdByPostId = jpaPostRepository.findUserIdByPostId(postId);
        Optional<User> byId = userRepository.findById(userIdByPostId);
        if (byId.isPresent()) {
            return cloudinary.url().version(redisService.getVersion("postId_"+postId)).forceVersion(true)
                    .transformation(new Transformation()
                            .width("500")
                            .height("500"))
                    .generate("post/" +postId+"/PostPic");
        }
        return null;
    }


    public void setPostPicture(MultipartFile file, Long postId){
        try {
            cloudinary.api().createFolder("post/"+postId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map map = ObjectUtils.asMap(
                "public_id", "PostPic",
                "folder", "post/" + postId);
        try {
            Map upload = cloudinary.uploader().upload(file.getBytes(), map);
            String version = upload.get("version").toString();
//            log.info("version ={}",version);
//            log.info("uploadfile = {}",upload);
            redisService.setVersion(version, "postId_"+postId);
            //버전 따로 저장해야함
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void setPicture(MultipartFile file){
        String usernameOnSession = userService.getUsernameOnSession();
        try {
            cloudinary.api().createFolder("images/" + usernameOnSession, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        try {
//            ApiResponse apiResponse = cloudinary.api().deleteResources(List.of("images/" + usernameOnSession + "/" + usernameOnSession + "_pic"),
//                    ObjectUtils.emptyMap());
//            log.info("delete response = {}", apiResponse);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        // 자동으로 위에 덮어쓰기가 되어서 삭제 안 해도 됨

        Map map = ObjectUtils.asMap(
                "public_id", usernameOnSession + "_pic",
                "folder", "images/" + usernameOnSession);
        try {
            Map upload = cloudinary.uploader().upload(file.getBytes(), map);
            String version = upload.get("version").toString();
//            log.info("version ={}",version);
//            log.info("uploadfile = {}",upload);
            redisService.setVersion(version, usernameOnSession);
            //버전 따로 저장해야함
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
