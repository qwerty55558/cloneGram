package com.jsy.clonegram.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.SearchFolders;
import com.cloudinary.Transformation;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPictureService {

    private final Cloudinary cloudinary;
    private final UserService service;
    private final RedisService redisService;

    public String getPictureUrl(){
        String usernameOnSession = service.getUsernameOnSession();
        String image = cloudinary.url().version(redisService.getVersion(usernameOnSession)).forceVersion(true)
                .transformation(new Transformation()
                .width("25")
                .height("25")
                .quality("80"))
                .generate("images/" + usernameOnSession + "/" + usernameOnSession + "_pic");
        // force_version이 적용 안 됨
        log.info("img = {}",image);
        return image;
    }

    public void setPicture(MultipartFile file){
        String usernameOnSession = service.getUsernameOnSession();
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
