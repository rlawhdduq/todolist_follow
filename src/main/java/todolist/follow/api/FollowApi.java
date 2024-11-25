package todolist.follow.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import todolist.follow.dto.FollowDto;
import todolist.follow.service.FollowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
public class FollowApi {

    @Autowired
    private final FollowService followService;

    @GetMapping("/api/follow")
    public Map<String, Object> getFollow(@RequestBody FollowDto followDto)
    {
        Map<String, Object> followList = followService.getFollowing(followDto.getFollower_user_id());
        
        return followList;
    }

    @PostMapping("/api/ffff")
    public void insertFollow(@RequestBody FollowDto followDto) {
        followService.insert(followDto);
        return;
    }
    
    @DeleteMapping("/api/follow")
    public void deleteFollow(@RequestBody FollowDto followDto)
    {
        followService.delete(followDto);
        return;
    }
}
