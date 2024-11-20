package todolist.follow.service;

import org.springframework.kafka.support.Acknowledgment;

import todolist.follow.dto.FollowDto;
import todolist.follow.dto.redis.FollowListDto;
public interface FollowService {

    FollowListDto getFollowing(Long user_id);    // 친구 조회
    
}
