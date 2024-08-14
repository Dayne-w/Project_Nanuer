package server.nanuer_server.dto.Post;

import server.nanuer_server.domain.Progress;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.dto.User.UserInfoDto;
import server.nanuer_server.dto.category.CategoryDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private int view;
    private int heartCount;
    private Progress progress;
    private int costInfo;
    private int total;
    private int deliveryCost;
    private String location;
    private String time;
    private int postStatus;
    private UserInfoDto userInfoDto;
    private CategoryDto categoryDto;

    public PostEntity toEntity(){
        PostEntity postEntity = PostEntity.builder()
                .postId(postId)
                .title(title)
                .content(content)
                .view(view)
                .heartCount(heartCount)
                .progress(progress)
                .costInfo(costInfo)
                .total(total)
                .deliveryCost(deliveryCost)
                .location(location)
                .time(time)
                .postStatus(postStatus)
                .userEntity(userInfoDto.toEntity())
                .categoryEntity(categoryDto.toEntity())
                .build();
        return postEntity;
    }
}
