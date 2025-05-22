package TP_Final.devhire.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "likes")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_user_like"))
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "publication_id", foreignKey = @ForeignKey(name = "FK_publication_like"))
    private PublicationEntity publication;
    private Timestamp likeDate;
    @NotNull
    private Boolean state;
}
