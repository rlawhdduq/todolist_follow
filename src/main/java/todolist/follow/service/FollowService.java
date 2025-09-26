package todolist.follow.service;

import java.util.List;
import java.util.Map;

import todolist.follow.dto.FollowDto;

public interface FollowService {

    // void insert(FollowDto followDto);
    // void delete(FollowDto followDto);

    String insert(FollowDto followDto);
    String delete(FollowDto followDto);
    Map<String, List<Long>> getFollowing(Long user_id);    // 친구 조회
    
}
