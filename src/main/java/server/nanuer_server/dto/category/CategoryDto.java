package server.nanuer_server.dto.category;

import server.nanuer_server.domain.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Integer categoryId;
    private String categoryName;

    public CategoryEntity toEntity(){
        CategoryEntity category = CategoryEntity.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
        return category;
    }
}
