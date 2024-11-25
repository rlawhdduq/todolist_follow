package todolist.follow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import todolist.follow.domain.Follow;
import todolist.follow.dto.FollowDto;
import todolist.follow.dto.redis.FollowListDto;
import todolist.follow.repository.FollowRepository;
import todolist.follow.service.FollowService;
import todolist.follow.service.KafkaProducer;

import java.util.ArrayList;
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
    @Autowired
    private KafkaProducer kafka;

    @Override
    public void insert(FollowDto followDto)
    {
        log.info("FollowService 들어옴");
        callKafka("follow-insert", (Object) followDto);
        log.info("FollowService 나감");
    }
    @Override
    public void delete(FollowDto followDto)
    {
        callKafka("follow-delete", (Object) followDto);
    }

    @KafkaListener
    (
        topics = "follow-insert", 
        groupId = "follow",
        containerFactory = "followDtoKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertFollowing(FollowDto followDto, Acknowledgment ack)
    {
        Follow insFollow = Follow.builder()
                                 .following_user_id(followDto.getFollowing_user_id())
                                 .follower_user_id(followDto.getFollower_user_id())
                                 .build();
        repoIns(insFollow);
        checkFollower(followDto, "INS");
        // user 서비스에 number_of_following과 number_of_follower 증가시켜줘야함
        // following_user_id는 number_of_follower, follower_user_id는 number_of_following을 증가시켜야 한다.
        ack.acknowledge();
    }

    @KafkaListener
    (
        topics = "follow-delete", 
        groupId = "follow",
        containerFactory = "followDtoKafkaListenerContainerFactory"
    )
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteFollowing(FollowDto followDto, Acknowledgment ack)
    {
        repoDel(followDto);
        checkFollower(followDto, "DEL");
        // user 서비스에 number_of_following과 number_of_follower 증가시켜줘야함
        // following_user_id는 number_of_follower, follower_user_id는 number_of_following을 증가시켜야 한다.
        ack.acknowledge();
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
    public Map<String, Object> getFollowing(Long user_id)
    {
        Map<String, Object> followList = new HashMap<>();
        followList.put("A", followRepository.getFollowing(user_id, 'A'));
        followList.put("F", followRepository.getFollowing(user_id, 'Y'));
        
        return followList;
    }

    // Private Method
    private void callKafka(String topic, Object dto)
    {
        log.info("Kafka 들어옴");
        kafka.sendMessage(topic, dto);
        log.info("Kafka 나감");
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
