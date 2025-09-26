package todolist.follow.api.rest;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class FollowApi {

    @Autowired
    private final FollowService followService;

    @RequestMapping(method=RequestMethod.GET)
    public Map<String, List<Long>> followGetCall(@RequestParam Long user_id)
    {
        Map<String, List<Long>> followList = followService.getFollowing(user_id);
        
        return followList;
    }

    @RequestMapping(method=RequestMethod.POST)
    public void followPostCall(@RequestBody FollowDto followDto) {
        followService.insert(followDto);
        return;
    }
    
    @RequestMapping(method=RequestMethod.DELETE)
    public void followDeleteCall(@RequestBody FollowDto followDto)
    {
        followService.delete(followDto);
        return;
    }
}
