package server.nanuer_server.dto.heart;

import server.nanuer_server.domain.entity.HeartEntity;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddHeartDto {

    private int postId;
    private UserEntity userEntity;
    private PostEntity postEntity;

    public HeartEntity toEntity(){
        HeartEntity heartEntity = HeartEntity.builder()
                .userEntity(userEntity)
                .postEntity(postEntity)
                .build();
        return heartEntity;
    }
}
