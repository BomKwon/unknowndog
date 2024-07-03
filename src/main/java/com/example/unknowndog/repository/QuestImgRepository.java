package com.example.unknowndog.repository;

import com.example.unknowndog.entity.QuestImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestImgRepository extends JpaRepository<QuestImg, Long> {

    // "selet i form ItemImg i where i.item_id = :itemId"

    List<QuestImg> findByQuestIdOrderByIdAsc(Long questId);

    //상품의 대표이미지를 찾는 쿼리 메소드
    QuestImg findByQuestIdAndRepimgYn (Long questId, String repimgYn);


}
