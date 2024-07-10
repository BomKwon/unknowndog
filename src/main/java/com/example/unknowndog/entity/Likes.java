package com.example.unknowndog.entity;


import com.example.unknowndog.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity //jpa에서 관리를 할수 있습니다. 엔티티매니저
@Table(name = "likes")   //jpa를 이용할 때 자동으로 데이터베이스 설정과 데이터베이스 내 테이블을 같이 확인하기때문에 에러 나올수 있음
// 데이터베이스상 어떤 테이블로 생성할 것인 정보를 담기 위한 어노테이션
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Likes extends BaseEntity {

    @Id
    @Column(name = "likes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //회원

    @OneToMany(mappedBy = "likes" , cascade = CascadeType.ALL, orphanRemoval = true
            , fetch = FetchType.LAZY)
    private List<LikeQuest> likeQuests = new ArrayList<>();

    //장바구니 만들기
    public static Likes createLike(User user) {
        Likes likes = new Likes();
        likes.setUser(user);
        return likes;
    }

}
