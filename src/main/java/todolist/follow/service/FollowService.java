package todolist.follow.service;

import java.util.Map;

import todolist.follow.dto.FollowDto;

public interface FollowService {

    void insert(FollowDto followDto);
    void delete(FollowDto followDto);
    Map<String, Object> getFollowing(Long user_id);    // 친구 조회
    
}
