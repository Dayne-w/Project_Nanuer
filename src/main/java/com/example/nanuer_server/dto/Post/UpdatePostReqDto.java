package com.example.nanuer_server.dto.Post;

import com.example.nanuer_server.domain.Progress;
import com.example.nanuer_server.domain.entity.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpdatePostReqDto {

    private String title;

    private String content;

    @JsonProperty("cost_info")
    private String costInfo;

    private String menu;

    private int total;

    @JsonProperty("delivery_cost")
    private String deliveryCost;

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
