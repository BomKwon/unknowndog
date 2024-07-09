package com.example.unknowndog.entity;

import com.example.unknowndog.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity //jpa에서 관리를 할수 있습니다. 엔티티매니저
@Table(name = "like_quest")   //jpa를 이용할 때 자동으로 데이터베이스 설정과 데이터베이스 내 테이블을 같이 확인하기때문에 에러 나올수 있음
// 데이터베이스상 어떤 테이블로 생성할 것인 정보를 담기 위한 어노테이션
@Getter
@Setter
@ToString
@NoArgsConstructor
public class LikeQuest extends BaseEntity {


    @Id
    @Column(name = "like_quest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //상품코드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "likes_id")
    private Likes likes;              // 장바구니

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    private Quest quest;              //아이템


    private int count;  //수량 장바구니 제품당 담는 수량


    // 카트에 담길 아이템을 참조하는 cartItem
    public static LikeQuest createLikeQuest (Likes likes, Quest quest, int count) {
        LikeQuest likeQuest = new LikeQuest();
        likeQuest.setLikes(likes);
        likeQuest.setQuest(quest);
        return likeQuest;

        // CartItem cartItem = CartItem.createCartItem(cart, item, count);
        //만들어진 객체로 저장을 할껄?
    }


    public void addCount (int count) {
        this.count += count;
    }

    public void updateCount(int count){
        this.count = count;
    }




}
