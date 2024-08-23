package server.nanuer_server.domain.entity;

import server.nanuer_server.domain.BaseTimeEntity;
import server.nanuer_server.domain.Progress;
import server.nanuer_server.dto.post.PostDto;
import server.nanuer_server.dto.post.UpdatePostReqDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@ToString(callSuper = true) // 부모 클래스의 toString 불러오는 어노테이션. 붙이면 createdAt 하고 updatedAt 데이터 정상적으로 나옴.
@EqualsAndHashCode(callSuper = true) // 부모클래스의 equalsAndHashCode 불러오는 어노테이션.
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    private String title;

    private String content;

    @Column(nullable = true)
    private int view;

    //**
    @Column(nullable = true)
    private int heartCount;

    @Column(name = "progress")
    @Enumerated(EnumType.STRING)
    private Progress progress;

    @JsonProperty("cost_info")
    private int costInfo;

    private String menu;

    private int total;

    @JsonProperty("delivery_cost")
    private int deliveryCost;

    private String location;

    private String time;

    @JsonProperty("post_status")
    private int postStatus;

    // userEntity를 삭제하면, 해당 유저가 작성한 postStatus값이 0으로 바뀌게 하는데, 이때 post가 완전히 삭제된 것이 아니라,
    // 이미 삭제된 userId값이 postEntity에 남아있게 되고, postEntity를 조회할 때, 해당 userId값을 가진 userEntity를 찾을 수 없다는
    // 에러가 발생한다. 이를 해결하기 위해 붙인 것이 @NotFound(action = NotFoundAction.IGNORE)
    @NotFound(action = NotFoundAction.IGNORE)
    //@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private UserEntity userEntity;

    @OneToOne
    @ToString.Exclude
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    //채팅 ******
    @OneToMany(mappedBy = "postEntity")
    @JsonIgnore
    @ToString.Exclude
    private List<ChatRoomEntity> chatRoomEntityList;


    public void update(UpdatePostReqDto updatePostReqDto) {
        this.title = updatePostReqDto.getTitle();
        this.content = updatePostReqDto.getContent();
        this.costInfo = updatePostReqDto.getCostInfo();
        this.menu = updatePostReqDto.getMenu();
        this.total = updatePostReqDto.getTotal();
        this.deliveryCost = updatePostReqDto.getDeliveryCost();
        this.time = updatePostReqDto.getTime();
        this.location = updatePostReqDto.getLocation();
        this.categoryEntity = updatePostReqDto.getCategoryEntity();
        this.progress = updatePostReqDto.getProgress();
    }

    public void delete() {
        this.postStatus = 0;
    }

    public void increaseView() {
        this.view += 1;
    }

    public void updateProgress(int progressId) {
        if (progressId == 1)
            this.progress = Progress.Confirm;
        else if (progressId == 2)
            this.progress = Progress.End;
    }

    public PostDto toDto() {
        PostDto postDto = PostDto.builder()
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
                .userInfoDto(userEntity.toDto())
                .categoryDto(categoryEntity.toDto())
                .build();
        return postDto;
    }

}