package todolist.follow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import todolist.follow.domain.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>{

    @Query("Select following_user_id From Follow Where follower_user_id = :user_id and follow_status = :follow_status and status = 'Y' ")
    List<Long> getFollowing(@Param("user_id") Long user_id, @Param("follow_status") Character follow_status);
    

    @Modifying
    @Query(value = "Delete From follow Where following_user_id = :following_user_id and follower_user_id = :follower_user_id", nativeQuery = true)
    void deleteFollow(@Param("following_user_id") Long following_user_id, @Param("follower_user_id") Long follower_user_id);

    /*
     * A가 B를 팔로잉하는 상황
     * B는 A를 이미 팔로잉 한 상황
     * 팔로우 등록할 땐 A가 follower B가 following
     * 팔로우 여부 확인할 땐 A가 following B가 follower
     * 1이면 true 0orNull이면 false
     */
    @Query("Select Exists (Select 1 From Follow Where following_user_id = :follower_user_id and follower_user_id = :following_user_id and follow_status = :follow_status)")
    @Lock(LockModeType.PESSIMISTIC_READ)
    Boolean findByFollower(@Param("follower_user_id") Long follower_user_id, @Param("following_user_id") Long following_user_id, @Param("follow_status") Character follow_status);

    @Modifying
    @Query(value = "Update follow Set follow_status ='Y' Where (following_user_id = :following_user_id and follower_user_id = :follower_user_id and follow_status = 'A') or (following_user_id = :follower_user_id and follower_user_id = :following_user_id and follow_status = 'A')", nativeQuery = true)
    void updateFollower(@Param("following_user_id") Long following_user_id, @Param("follower_user_id") Long follower_user_id);

    @Modifying
    @Query(value = "Update follow Set follow_status = 'A' Where follower_user_id = :following_user_id and following_user_id = :follower_user_id and status = 'Y'", nativeQuery = true)
    void cancelFollower(@Param("following_user_id") Long following_user_id, @Param("follower_user_id") Long follower_user_id);
}
