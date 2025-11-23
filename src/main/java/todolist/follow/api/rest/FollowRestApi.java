package todolist.follow.api.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import todolist.follow.dto.FollowDto;
import todolist.follow.service.rest.FollowService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class FollowRestApi {

    @Autowired
    private final FollowService followService;

    @RequestMapping(path="/{user_id}", method=RequestMethod.GET)
    public Map<String, List<Long>> followGetCall(@PathVariable Long user_id)
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

    @RequestMapping(path="/state", method=RequestMethod.GET)
    public Boolean followState(@RequestParam("target") Long target_user_id, @RequestParam("source") Long source_user_id)
    {
        Boolean followState = followService.followState(target_user_id, source_user_id);
        return followState;
    }
}
