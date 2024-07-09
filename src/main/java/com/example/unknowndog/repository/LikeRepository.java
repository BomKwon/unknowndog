package com.example.unknowndog.repository;

import com.example.unknowndog.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long> {


    // userId로 저장된  카트 객체 가져오기
    // 장바구니의 id를 likeQuest 참조
    // email로 찾으려면 CreateBy
    Likes findByUserId (Long UserId);

//    Cart findByCreateBy (Long memberuserId);

}
