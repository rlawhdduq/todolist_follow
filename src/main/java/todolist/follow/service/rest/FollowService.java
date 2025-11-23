package todolist.follow.service.rest;

import java.util.List;
import java.util.Map;

import todolist.follow.dto.FollowDto;

public interface FollowService {

    String insert(FollowDto followDto);
    String delete(FollowDto followDto);
    Map<String, List<Long>> getFollowing(Long user_id);    // 친구 조회
    Boolean followState(Long target_user_id, Long source_user_id); // 친구 상태 확인
}
