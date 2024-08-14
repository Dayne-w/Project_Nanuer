package server.nanuer_server.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;


@Table(name="room")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@DynamicUpdate
@DynamicInsert
@Entity
@ToString(callSuper = true) // 부모 클래스의 toString 불러오는 어노테이션. 붙이면 createdAt 하고 updatedAt 데이터 정상적으로 나옴.
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int roomId;

    private int roomNumber;

    private Boolean isWriter;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    @ToString.Exclude
    private PostEntity postEntity;



    public static ChatRoomEntity create(Boolean isWriter,UserEntity userEntity, PostEntity postEntity){
        ChatRoomEntity room = new ChatRoomEntity();
        room.userEntity = userEntity;
        room.postEntity = postEntity;
        room.roomNumber = postEntity.getPostId();
        room.isWriter = isWriter;
        return room;
    }


}