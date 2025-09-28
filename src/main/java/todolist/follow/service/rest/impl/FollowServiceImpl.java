package todolist.follow.service.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import todolist.follow.domain.Follow;
import todolist.follow.dto.FollowDto;
import todolist.follow.dto.redis.FollowListDto;
import todolist.follow.repository.FollowRepository;
import todolist.follow.service.rest.FollowService;
// import todolist.follow.service.KafkaProducer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("restService")
public class FollowServiceImpl implements FollowService{
    
    private static final Logger log = LoggerFactory.getLogger(FollowServiceImpl.class);

    @Autowired
    private FollowRepository followRepository;

    @Override
    public String insert(FollowDto followDto)
    {
        Follow insFollow = Follow.builder()
                                 .following_user_id(followDto.getFollowing_user_id())
                                 .follower_user_id(followDto.getFollower_user_id())
                                 .build();
        repoIns(insFollow);
        checkFollower(followDto, "INS");
        return "정상처리되었습니다.";
    }
    @Override
    public String delete(FollowDto followDto)
    {
        repoDel(followDto);
        checkFollower(followDto, "DEL");
        return "정상처리되었습니다.";
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void checkFollower(FollowDto followDto, String operFlag)
    {
        if( operFlag.equals("INS") )
        {
            if(followRepository.findByFollower(followDto.getFollower_user_id(), followDto.getFollowing_user_id(), 'A'))
            {
                followRepository.updateFollower(followDto.getFollowing_user_id(), followDto.getFollower_user_id());
            }
        }
        else if( operFlag.equals("DEL") )
        {
            if(followRepository.findByFollower(followDto.getFollower_user_id(), followDto.getFollowing_user_id(), 'Y'))
            {
                followRepository.cancelFollower(followDto.getFollowing_user_id(), followDto.getFollower_user_id());
            }
        }
    }

    @Override 
    public Map<String, List<Long>> getFollowing(Long user_id)
    {
        Map<String, List<Long>> followList = new HashMap<>();
        followList.put("A", followRepository.getFollowing(user_id, 'A'));
        followList.put("F", followRepository.getFollowing(user_id, 'Y'));
        
        return followList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void repoIns(Follow follow)
    {
        followRepository.save(follow);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    private void repoDel(FollowDto followDto)
    {
        followRepository.deleteFollow(followDto.getFollowing_user_id(), followDto.getFollower_user_id());
    }
}
