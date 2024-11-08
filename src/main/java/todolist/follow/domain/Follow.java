package todolist.follow.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@Table(name = "follow")
public class Follow {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long follow_id;

    @Column(nullable = false)
    private Long following_user_id;

    @Column(nullable = false)
    private Long follower_user_id;

    @Column(insertable = false, updatable = true)
    private Character follow_status;

    @Column(insertable = false, updatable = true)
    private LocalDateTime following_create_time;

    @Column(insertable = false, updatable = true)
    private Character status;

    @Column(insertable = false, updatable = true)
    private LocalDateTime update_time;

}
