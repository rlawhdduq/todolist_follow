package todolist.follow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import todolist.follow.domain.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>{

    @Query("Select following_user_id From Follow Where follower_user_id = :user_id and status = 'Y' ")
    List<Long> getFollowing(@Param("user_id") Long user_id);

}
