package todolist.follow.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import todolist.follow.dto.FollowDto;
import todolist.follow.dto.redis.FollowListDto;
import todolist.follow.service.FollowService;

@RestController
public class FollowApi {

    @Autowired
    private FollowService followService;
    
    @PostMapping("/api/follow")
    public void postMethodName(@RequestBody FollowDto followDto)
    {

    }

    @DeleteMapping("/api/follow")
    public void deleteMethodName(@RequestBody FollowDto followDto)
    {

    }

    @GetMapping("/api/follow/{user_id}")
    public FollowListDto getMethodName(@PathVariable Long user_id)
    {
        FollowListDto followListDto = followService.getFollowing(user_id);
        return followListDto;
    }

}
