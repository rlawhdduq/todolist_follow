package todolist.follow.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import todolist.follow.dto.FollowDto;
import todolist.follow.service.FollowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class FollowApi {

    @Autowired
    private FollowService followService;
    private static final Logger log = LoggerFactory.getLogger(FollowApi.class);

    @GetMapping("/api/follow/{user_id}")
    public Map<String, Object> getFollow(@PathVariable Long user_id)
    {
        Map<String, Object> followList = followService.getFollowing(user_id);
        
        return followList;
    }

    @PostMapping("/api/follow")
    public void insertFollow(@RequestBody FollowDto followDto) {
        System.out.println("FollowApi 들어옴");
        followService.insert(followDto);
        System.out.println("FollowApi 나감");
        return;
    }
    
    @DeleteMapping("/api/follow")
    public void deleteFollow(@RequestBody FollowDto followDto)
    {
        followService.delete(followDto);
        return;
    }
}
