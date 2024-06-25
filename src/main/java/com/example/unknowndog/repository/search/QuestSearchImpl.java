package com.example.unknowndog.repository.search;

import com.example.unknowndog.dto.QuestSearchDTO;
import com.example.unknowndog.entity.QQuest;
import com.example.unknowndog.entity.Quest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class QuestSearchImpl extends QuerydslRepositorySupport implements QuestSearch {


  public QuestSearchImpl() {
    super(Quest.class);
  }




  @Override
  public Page<Quest> jpqlQuerygetQuestPage(QuestSearchDTO questSearchDTO, Pageable pageable) {

    QQuest quest = QQuest.quest; //q 도메인 객체 entity를 q로 바꾼것
    JPQLQuery<Quest> query = from(quest);

    BooleanBuilder booleanBuilder =new BooleanBuilder();
    LocalDateTime localDateTime = LocalDateTime.now();// 현재

    if(questSearchDTO.getQuestStatus() != null){

      booleanBuilder.and(quest.questStatus.eq(questSearchDTO.getQuestStatus()));

    }

    if(StringUtils.equals("all", questSearchDTO.getSearchDateType())  || questSearchDTO.getSearchDateType() == null){

    }else {
      if(StringUtils.equals("1d", questSearchDTO.getSearchDateType())){
        localDateTime = localDateTime.minusDays(1);
      }else if(StringUtils.equals("1w", questSearchDTO.getSearchDateType())){
        localDateTime = localDateTime.minusWeeks(1);
      }else if(StringUtils.equals("1m", questSearchDTO.getSearchDateType())){
        localDateTime = localDateTime.minusMonths(1);
      }else if(StringUtils.equals("6m", questSearchDTO.getSearchDateType())){
        localDateTime = localDateTime.minusMonths(6);
      }

      booleanBuilder.and(quest.regTime.after(localDateTime));
    }

    if(StringUtils.equals("title", questSearchDTO.getSearchBy())){
      booleanBuilder.and(quest.title.like("%" + questSearchDTO.getSearchQuery()+ "%"));

    }else if(StringUtils.equals("area",  questSearchDTO.getSearchBy()) ){

      booleanBuilder.and(quest.area.like("%" + questSearchDTO.getSearchQuery()+ "%"));

    }

    query.where(booleanBuilder);
    System.out.println("검색조건 추가 : " + query);


    query.where(quest.id.gt(0L));
    System.out.println("0보다 큰 조건 id가 " + query);

    //페이징
    this.getQuerydsl().applyPagination(pageable, query);

    List<Quest> content = query.fetch(); //실행
//    boardList.forEach(board1 -> log.info(board1));
    long count = query.fetchCount(); //row 수
//    System.out.println(count);


    return new PageImpl<>(content, pageable, count);
  }

}
