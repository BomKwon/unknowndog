package com.example.unknowndog.repository;

import com.example.unknowndog.dto.LikeDetailDTO;
import com.example.unknowndog.entity.LikeQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeQuestRepository extends JpaRepository<LikeQuest, Long> {

    LikeQuest findByLikes_IdAndQuestId (Long likesId, Long QuestId);
    //이미 상품이 장바구니에 있다면 add를 해주기 위해서findByLikesIdAndQuestId



    // 장바구니 페이지에 전달할 CartDetailDTO 리스트를 쿼리 하나로 조회하는 jpQL문
    //Long cartItemId, String itemNm, int price, int count, String imgUrl
    @Query("select new com.example.unknowndog.dto.LikeDetailDTO(lq.id, lq.quest.id, q.title, q.salary, lq.count, qi.imgUrl, q.area, q.questStatus, q.salaryOption) " +
            "from LikeQuest lq , QuestImg qi join lq.quest q " +
            "where lq.likes.id = :likesId and qi.quest.id = lq.quest.id " +
            "and qi.repimgYn = 'Y' order by lq.regTime desc")
    List<LikeDetailDTO> finaaaaa(Long likesId);




    LikeQuest findByCreateBy (String email);

}
