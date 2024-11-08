package todolist.follow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import todolist.follow.dto.redis.FollowListDto;
import todolist.follow.repository.FollowRepository;
import todolist.follow.service.FollowService;
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
        FollowListDto redisDto = new FollowListDto();
        redisDto.setUser_id(user_id);
        redisDto.setUserList(followRepository.getFollowing(user_id));
        return redisDto;
    }

}
