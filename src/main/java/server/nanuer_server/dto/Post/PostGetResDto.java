package server.nanuer_server.dto.Post;

import server.nanuer_server.domain.entity.CategoryEntity;
import lombok.*;
import server.nanuer_server.domain.entity.UserEntity;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostGetResDto {

    private Integer post_id;
    private String title;
    private int view;
    private String progress;
    private int total;
    private String location;
    private LocalDateTime modified_date;
    private UserEntity userEntity;
    private CategoryEntity categoryEntity;

    @Builder
    public PostGetResDto(int post_id, String title, int view, String progress, int total, String location,
                         LocalDateTime modified_date, UserEntity userEntity, CategoryEntity categoryEntity) {
        this.post_id = post_id;
        this.title = title;
        this.view = view;
        this.progress = progress;
        this.total = total;
        this.location = location;
        this.modified_date = modified_date;
        this.userEntity = userEntity;
        this.categoryEntity = categoryEntity;
    }

    public PostGetResDto toEntity() {
        return PostGetResDto.builder()
                .post_id(post_id)
                .title(title)
                .view(view)
                .progress(progress)
                .total(total)
                .location(location)
                .modified_date(modified_date)
                .userEntity(userEntity)
                .categoryEntity(categoryEntity)
                .build();
    }

}


