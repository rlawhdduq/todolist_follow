package todolist.follow.service;

import todolist.follow.dto.redis.FollowListDto;
public interface FollowService {

    void insertFollowing();                 // 친구 등록
    void deleteFollowing();                 // 친구 삭제
    FollowListDto getFollowing(Long user_id);    // 친구 조회
    
}
