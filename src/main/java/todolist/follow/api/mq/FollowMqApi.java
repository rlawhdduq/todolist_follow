package todolist.follow.api.mq;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import todolist.follow.dto.FollowDto;
import todolist.follow.service.mq.FollowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
public class FollowMqApi {

    @Autowired
    private final FollowService followService;

    @GetMapping("/api/follow")
    public Map<String, List<Long>> getFollow(@RequestParam Long user_id)
    {
        Map<String, List<Long>> followList = followService.getFollowing(user_id);
        
        return followList;
    }

    @PostMapping("/api/follow")
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
