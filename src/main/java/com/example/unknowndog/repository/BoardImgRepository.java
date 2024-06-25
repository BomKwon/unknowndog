package com.example.unknowndog.repository;

import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.BoardImg;
import com.example.unknowndog.entity.Quest;
import com.example.unknowndog.entity.QuestImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImgRepository extends JpaRepository<BoardImg, Long> {

    List<BoardImg> findByBoardIdOrderByIdAsc(Long boardId);

    //상품의 대표이미지를 찾는 쿼리 메소드
    Board findByBoardIdAndRepimgYn (Long boardId, String repimgYn);


}
