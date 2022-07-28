package com.example.nanuer_server.domain.entity;

import com.example.nanuer_server.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Table(name="like")
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Data
@Entity
@ToString(callSuper = true) // 부모 클래스의 toString 불러오는 어노테이션. 붙이면 createdAt 하고 updatedAt 데이터 정상적으로 나옴.
@EqualsAndHashCode(callSuper = true) // 부모클래스의 equalsAndHashCode 불러오는 어노테이션.
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private int likeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    @ToString.Exclude
    private Post postEntity;

}