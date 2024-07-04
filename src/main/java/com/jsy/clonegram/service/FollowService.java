package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Follow;
import com.jsy.clonegram.dao.User;
import com.jsy.clonegram.dto.FollowSetDto;
import com.jsy.clonegram.dto.UserProfileDto;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {

    private final RedisService redisService;
    private final UserRepository userRepository;
    private final NotificationsService notificationsService;
    private final UserService userService;


    @Transactional
    public String followUser(Long followingId){
        Long followerId = userService.getUserIdOnSession();
        Optional<Follow> first = userRepository.findFollowingsByUserId(followerId).stream().filter(c -> c.getUserId().equals(followingId)).findFirst();

        if (first.isEmpty()) {
            Follow follow = new Follow();
            follow.setFollowerId(followerId);
            follow.setUserId(followingId);

            userRepository.followUser(follow);

            Long followingSize = (long) userRepository.findFollowingsByUserId(followerId).size(); // findfollowings (나를팔로잉하는사람을찾음)
            Long followerSize = (long) userRepository.findFollowersByUserId(followingId).size(); //

            log.info("followingsize = {}",followingSize);
            log.info("followerize = {}",followerSize);

            redisService.setFollower(followingId,followerSize);
            redisService.setFollowing(followerId,followingSize);

            notificationsService.notifyFollow(follow);
            return "success";
        }

        return "fail";
    }

    @Transactional
    public String unfollowUser(Long followingId){
        try{
            Long followerId = userService.getUserIdOnSession();

            Follow follow = new Follow();
            follow.setUserId(followingId);
            follow.setFollowerId(followerId);

            userRepository.unfollowUser(follow);

            Long followingSize = (long)userRepository.findFollowingsByUserId(followerId).size();
            Long followerSize = (long)userRepository.findFollowersByUserId(followingId).size();

            redisService.setFollower(followingId, followerSize);
            redisService.setFollowing(followerId, followingSize);

            return "success";
        }catch(Exception e){
            return "fail";
        }
    }

    public List<UserProfileDto> getMyFollower(){
        LinkedList<UserProfileDto> userProfileDtos = new LinkedList<>();

        Long userIdOnSession = userService.getUserIdOnSession();
        userRepository.findFollowersByUserId(userIdOnSession).stream().distinct().map(c -> c.getFollowerId()).forEach(c -> {
            Optional<User> byId = userRepository.findById(c);
            if (byId.isPresent()) {
                UserProfileDto userProfileDto = new UserProfileDto();
                userProfileDto.setUserName(byId.get().getUserName());
                userProfileDto.setUserPic(byId.get().getProfileImageUrl());
                userProfileDto.setUserId(byId.get().getId());
                userProfileDtos.add(userProfileDto);
            }
        });
        return userProfileDtos;
    }

    public List<UserProfileDto> getMyFollowing(){
        LinkedList<UserProfileDto> userProfileDtos = new LinkedList<>();

        Long userIdOnSession = userService.getUserIdOnSession();
        userRepository.findFollowingsByUserId(userIdOnSession).stream().distinct().map(c -> c.getUserId()).forEach(c -> {
            Optional<User> byId = userRepository.findById(c);
            if (byId.isPresent()) {
                UserProfileDto userProfileDto = new UserProfileDto();
                userProfileDto.setUserName(byId.get().getUserName());
                userProfileDto.setUserPic(byId.get().getProfileImageUrl());
                userProfileDto.setUserId(byId.get().getId());
                userProfileDtos.add(userProfileDto);
            }
        });
        return userProfileDtos;
    }

    public Long getFollowerCount(Long userId){
        return Long.parseLong(redisService.getFollower(userId));
    }

    public Long getFollowingCount(Long userId){
        return Long.parseLong(redisService.getFollowing(userId));
    }

    @Transactional
    public Boolean checkFollowStatus(Long userId, Long targetId){
        Optional<Follow> first = userRepository.findFollowingsByUserId(userId).stream().filter(c -> c.getUserId().equals(targetId)).findFirst();
        return first.isPresent();
    }

    public FollowSetDto getFollowSet(){
        Long id = userService.getUserIdOnSession();

        FollowSetDto followSetDto = new FollowSetDto();
        followSetDto.setFollower(getFollowerCount(id));
        followSetDto.setFollowing(getFollowingCount(id));

        return followSetDto;
    }

}
