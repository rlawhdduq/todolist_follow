package todolist.follow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import todolist.follow.dto.FollowDto;

@SpringBootTest
@AutoConfigureMockMvc
public class FollowServiceImplTest {
    
    private static final Logger log = LoggerFactory.getLogger(FollowServiceImplTest.class);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private KafkaProducer kafka;

    // @Test
    public void getFollowing()
    throws Exception
    {
        Long user_id = 100L;
        log.info("token CreateStart");
        ResultActions resultActions = mockMvc.perform(get("/api/follow/"+user_id)
                                            .contentType(MediaType.APPLICATION_JSON))
                                            .andExpect(status().isOk());
        log.info("return json : " + resultActions.andReturn().getResponse().getContentAsString());
        log.info("token CreateEnd");
    }
    @Test
    public void insertFollow()
    throws Exception
    {
        log.info("테스트 코드 시작");
        List<Long> arr = java.util.Arrays.asList(7777L, 594201L, 5358L, 113729L, 3729L);
        FollowDto followDto = new FollowDto();
        followDto.setFollower_user_id(7777L);
        followDto.setFollowing_user_id(100L);
        String insertBoardRequest = objectMapper.writeValueAsString(followDto);

        ResultActions result = mockMvc.perform(post("/api/follow").contentType(MediaType.APPLICATION_JSON).content(insertBoardRequest));
        // log.info("결과 : " + result.andExpect(status().isCreated()));
        // for(Long follower_user_id : arr)
        // {
        //     log.info("포문 들어왔나?");
        //     log.info("포문 끝났나?");
        // }
        log.info("테스트 코드 종료");
    }

    // kafka Test
    // @Test
    public void insertFollowing()
    {
        String topic = "follow-insert";
        Long following_user_id = 11L;
        Long follower_user_id = 22L;
        FollowDto followDto = new FollowDto();
        followDto.setFollowing_user_id(following_user_id);
        followDto.setFollower_user_id(follower_user_id);

        kafka.sendMessage(topic, (Object) followDto);
    }

    // @Test
    public void deleteFollowing()
    {
        String topic = "follow-delete";
        Long following_user_id = 22L;
        Long follower_user_id = 11L;
        FollowDto followDto = new FollowDto();
        followDto.setFollowing_user_id(following_user_id);
        followDto.setFollower_user_id(follower_user_id);

        kafka.sendMessage(topic, (Object) followDto);
    }
}
