package server.nanuer_server.dto.Post;

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
public class PostChatDto {
    private int postId;
    private int userId;

    private UserEntity userEntity;

    public PostEntity toEntity(){
        PostEntity postEntity = PostEntity.builder()
                .postId(postId)
                .userEntity(userEntity)
                .build();
        return postEntity;
    }

}
