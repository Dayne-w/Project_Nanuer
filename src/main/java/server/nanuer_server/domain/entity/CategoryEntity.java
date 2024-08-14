package server.nanuer_server.domain.entity;

import server.nanuer_server.dto.category.CategoryDto;
import lombok.*;

import javax.persistence.*;

@Table(name="category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode
@Entity
public class CategoryEntity{
    @Id
    @Column(name="category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column(name="category_name",nullable = false)
    private String categoryName;

    //일대일, 양방향, 이름 수정 (mapped에는 테이블 이름을 앞에 소문자로 적어야하는듯..싶은..)
    //@OneToOne(mappedBy = "categoryEntity")
    //private PostEntity postEntity;

    public CategoryDto toDto(){
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
        return categoryDto;
    }
}
