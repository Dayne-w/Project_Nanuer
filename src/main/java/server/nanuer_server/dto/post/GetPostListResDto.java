package server.nanuer_server.dto.post;

import server.nanuer_server.domain.Progress;
import server.nanuer_server.domain.entity.CategoryEntity;
import server.nanuer_server.domain.entity.PostEntity;
import lombok.*;
import server.nanuer_server.domain.entity.UserEntity;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GetPostListResDto {

    private int postId;
    private String title;
    private int view;
    private Progress progress;
    private int total;
    private String location;
    private LocalDateTime modifiedDate;
    private UserEntity userEntity;
    private CategoryEntity categoryEntity;

    @Builder
    public GetPostListResDto(PostEntity entity) {
        this.postId = entity.getPostId();
        this.title = entity.getTitle();
        this.view = entity.getView();
        this.progress = entity.getProgress();
        this.total = entity.getTotal();
        this.location = entity.getLocation();
        this.modifiedDate = entity.getModified_date();
        this.userEntity = entity.getUserEntity();
        this.categoryEntity = entity.getCategoryEntity();
    }

}


