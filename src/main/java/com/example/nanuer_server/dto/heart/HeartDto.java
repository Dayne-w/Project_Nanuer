package com.example.nanuer_server.dto.heart;

import com.example.nanuer_server.domain.entity.HeartEntity;
import com.example.nanuer_server.dto.Post.PostDto;
import com.example.nanuer_server.dto.User.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeartDto {
    private Integer heartId;
    private UserInfoDto userInfoDto;
    private PostDto postDto;

    public HeartEntity toEntity(){
        HeartEntity heartEntity = HeartEntity.builder()
                .heartId(heartId)
                .userEntity(userInfoDto.toEntity())
                .postEntity(postDto.toEntity())
                .build();
        return heartEntity;
    }
}
