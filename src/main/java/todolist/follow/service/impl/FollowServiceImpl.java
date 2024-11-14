package todolist.follow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import todolist.follow.dto.redis.FollowListDto;
import todolist.follow.repository.FollowRepository;
import todolist.follow.service.FollowService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class FollowServiceImpl implements FollowService{
    
    private static final Logger log = LoggerFactory.getLogger(FollowServiceImpl.class);

    @Autowired
    private FollowRepository followRepository;

    @Override
    public void insertFollowing()
    {

    }

    @Override
    public void deleteFollowing()
    {}

    @Override 
    public FollowListDto getFollowing(Long user_id)
    {
        FollowListDto followListDto = new FollowListDto();
        
        followListDto.setUser_id(user_id);
        followListDto.setUserList(followRepository.getFollowing(user_id));

        return followListDto;
    }

}
