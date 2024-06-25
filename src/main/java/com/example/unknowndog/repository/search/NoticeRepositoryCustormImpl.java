package com.example.unknowndog.repository.search;

import com.example.unknowndog.constant.QuestStatus;
import com.example.unknowndog.dto.MainQuestDTO;
import com.example.unknowndog.dto.NoticeSearchDTO;
import com.example.unknowndog.dto.QMainQuestDTO;
import com.example.unknowndog.dto.QuestSearchDTO;
import com.example.unknowndog.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class NoticeRepositoryCustormImpl implements NoticeRepositoryCustorm {

    private JPAQueryFactory jpaQueryFactory;

    public NoticeRepositoryCustormImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    //상품명검색 like
    private BooleanExpression titleLike(String searchQuery){
        return StringUtils
                .isEmpty(searchQuery) ? null : QNotice.notice.title.like("%" + searchQuery + "%");
    }


//    private BooleanExpression searchSellStatusEq(QuestStatus questStatus){
//
//        // 입력값이 없으면 null 있으면 select * from item  where itemSellStatus = :itemSellStatus
//        return questStatus == null ? null : QQuest.quest.questStatus.eq(questStatus);
//    }


    private BooleanExpression regDtsAfter(String searchDateType){
        // 6개월전, 1년전 1달전 1주일전

        LocalDateTime localDateTime = LocalDateTime.now();// 현재

        if(StringUtils.equals("all", searchDateType) || searchDateType == null ){
            return null;        /////////////////////////// 여기다 지워야함
        }else if(StringUtils.equals("1d", searchDateType)  ){
            localDateTime = localDateTime.minusDays(1);
        }else if(StringUtils.equals("1w", searchDateType)  ){
            localDateTime = localDateTime.minusWeeks(1);
        }else if(StringUtils.equals("1m", searchDateType)  ){
            localDateTime = localDateTime.minusMonths(1);
        }else if(StringUtils.equals("6m", searchDateType)  ){
            localDateTime = localDateTime.minusMonths(6);
        }

        return QNotice.notice.regTime.after(localDateTime);

    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        // 제목 , 제목+내용 , 제목 + 내용 + 작성자   랑 검색어
        if(StringUtils.equals("title",  searchBy) ){

            return QNotice.notice.title.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("content",  searchBy) ){

            return QNotice.notice.content.like("%" + searchQuery + "%");
        }

        return null;

    }



    @Override
    public Page<Notice> getNoticePage(NoticeSearchDTO noticeSearchDTO, Pageable pageable) {
        QueryResults<Notice> result =  jpaQueryFactory.selectFrom(QNotice.notice)
                .where( regDtsAfter(noticeSearchDTO.getSearchDateType()),
                        searchByLike( noticeSearchDTO.getSearchBy(),
                                noticeSearchDTO.getSearchQuery() )
                )
                .orderBy(QNotice.notice.id.desc() )
                .offset(pageable.getOffset())           // 몇번부터 1번글부터 // 11번글 부터
                .limit(pageable.getPageSize())          //size = 10 10개씩
                .fetchResults();

        List<Notice> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);

    }

//    @Override
//    public Page<MainQuestDTO> getMainNoticePage(NoticeSearchDTO noticeSearchDTO, Pageable pageable) {
//        QQuest quest = QQuest.quest;
//        QQuestImg questImg = QQuestImg.questImg;
//
//        QueryResults<MainQuestDTO> result =  jpaQueryFactory.select(
//                    new QMainQuestDTO(
//                            quest.id,
//                            quest.writer,
//                            quest.title,
//                            quest.salaryOption,
//                            quest.salary,
//                            questImg.imgUrl,
//                            quest.area,
//                            quest.questDetail,
//                            quest.questStatus
//                    )
//                )
//                .from(questImg)
//                .join(questImg.quest, quest)
//                .where(questImg.repimgYn.eq("Y"))    //대표이미지
//                .where(titleLike(questSearchDTO.getSearchQuery()))  //상품명검색 입력받은것과 같은거
//                .orderBy(questImg.id.desc() )
//                .offset(pageable.getOffset())           // 몇번부터 1번글부터 // 11번글 부터
//                .limit(pageable.getPageSize())          //size = 10 10개씩
//                .fetchResults();
//
//        List<MainQuestDTO> content = result.getResults();
//        long total = result.getTotal();
//
//
//        return new PageImpl<>(content, pageable, total);
//    }
}
