package TP_Final.devhire.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "followers")
public class FollowEntity {
    @EmbeddedId
    private FollowId id;

    private LocalDateTime followUpDate;
    @NotNull
    private Boolean state;

    @ManyToOne
    @MapsId("follower_id")
    @JoinColumn(name = "follower_id")
    private UserEntity follower;

    @ManyToOne
    @MapsId("followed_id")
    @JoinColumn(name = "followed_id")
    private UserEntity followed;

    @NotNull
    private Boolean acepted;

    public FollowEntity(Boolean acepted, Boolean state, Timestamp followUpDate) {
        this.acepted = false;
        this.state = true;
        this.followUpDate = LocalDateTime.now();
    }

    public FollowEntity(UserEntity follower, UserEntity followed) {
        this.id = new FollowId(follower.getUser_id(), followed.getUser_id());
        this.followUpDate = LocalDateTime.now();
        this.state = true;
        this.follower = follower;
        this.followed = followed;
        this.acepted = false;
    }

}
