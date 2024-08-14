package server.nanuer_server.domain.entity;

import server.nanuer_server.domain.BaseTimeEntity;
import server.nanuer_server.dto.heart.HeartDto;
import lombok.*;

import javax.persistence.*;

@Table(name="heart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@ToString(callSuper = true) // 부모 클래스의 toString 불러오는 어노테이션. 붙이면 createdAt 하고 updatedAt 데이터 정상적으로 나옴.
@EqualsAndHashCode(callSuper = true) // 부모클래스의 equalsAndHashCode 불러오는 어노테이션.
public class HeartEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private int heartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    @ToString.Exclude
    private PostEntity postEntity;


    public HeartDto toDto(){
        HeartDto heartDto = HeartDto.builder()
                .heartId(heartId)
                .userInfoDto(userEntity.toDto())
                .postDto(postEntity.toDto())
                .build();
        return heartDto;
    }

}