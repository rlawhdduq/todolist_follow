package todolist.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowDto {

    private Long following_user_id;
    private Long follower_user_id;
    
}
