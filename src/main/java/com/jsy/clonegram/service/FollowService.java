package com.jsy.clonegram.service;

import com.jsy.clonegram.dao.Follow;
import com.jsy.clonegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {

    private final RedisService redisService;
    private final UserRepository userRepository;

    public void followUser(Long followerId, Long followingId){
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
    }

    public void unfollowUser(Long followerId, Long followingId){
        Follow follow = new Follow();
        follow.setUserId(followingId);
        follow.setFollowerId(followerId);
        userRepository.unfollowUser(follow);
        Long followingSize = (long)userRepository.findFollowingsByUserId(followerId).size();
        Long followerSize = (long)userRepository.findFollowersByUserId(followingId).size();
        redisService.setFollower(followingId, followerSize);
        redisService.setFollowing(followerId, followingSize);
    }

    public Long getFollowerCount(Long userId){
        return Long.parseLong(redisService.getFollower(userId));
    }

    public Long getFollowingCount(Long userId){
        return Long.parseLong(redisService.getFollowing(userId));
    }

    public Boolean checkFollowStatus(Long userId, Long targetId){
        Optional<Follow> first = userRepository.findFollowingsByUserId(userId).stream().filter(c -> c.getUserId().equals(targetId)).findFirst();
        return first.isPresent();
    }

}
