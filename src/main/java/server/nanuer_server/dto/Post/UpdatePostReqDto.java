package server.nanuer_server.dto.Post;

import server.nanuer_server.domain.Progress;
import server.nanuer_server.domain.entity.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpdatePostReqDto {

    private String title;

    private String content;

    @JsonProperty("cost_info")
    private int costInfo;

    private String menu;

    private int total;

    @JsonProperty("delivery_cost")
    private int deliveryCost;

    private String location;

    private String time;

    private CategoryEntity categoryEntity;

    @JsonProperty("category_id")
    private int categoryId;

    private Progress progress;

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }
}
