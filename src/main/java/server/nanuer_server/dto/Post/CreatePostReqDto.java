package server.nanuer_server.dto.Post;

import server.nanuer_server.domain.Progress;
import server.nanuer_server.domain.entity.CategoryEntity;
import server.nanuer_server.domain.entity.PostEntity;
import server.nanuer_server.domain.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreatePostReqDto {

    private String title;
    private String content;

    private int costInfo;
    private String menu;
    private int total;

    private int deliveryCost;
    private String location;
    private String time;
    private UserEntity userEntity;
    private CategoryEntity categoryEntity;

    private int userId;

    private int categoryId;

    private Progress progress;

    private int postStatus;

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public PostEntity toEntity() {
        return PostEntity.builder()
                .title(title)
                .content(content)
                .costInfo(costInfo)
                .menu(menu)
                .total(total)
                .deliveryCost(deliveryCost)
                .location(location)
                .time(time)
                .userEntity(userEntity)
                .categoryEntity(categoryEntity)
                .progress(progress.Recruit)
                .postStatus(1)
                .build();
    }
}
