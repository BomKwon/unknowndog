package com.example.unknowndog.repository.search;

import com.example.unknowndog.constant.BoardCategory;
import com.example.unknowndog.constant.QuestStatus;
import com.example.unknowndog.dto.BoardSearchDTO;
import com.example.unknowndog.dto.MainBoardDTO;
import com.example.unknowndog.dto.QMainBoardDTO;
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

public class BoardRepositoryCustormImpl implements BoardRepositoryCustorm{


    private JPAQueryFactory jpaQueryFactory;

    public BoardRepositoryCustormImpl(EntityManager em){
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

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

        return QBoard.board.regTime.after(localDateTime);

    }

    private BooleanExpression searchBoardCategory(BoardCategory boardCategory){

        // 입력값이 없으면 null 있으면 select * from item  where itemSellStatus = :itemSellStatus
        return boardCategory == null ? null : QBoard.board.boardCategory.eq(boardCategory);
    }


    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        // 제목 , 제목+내용 , 제목 + 내용 + 작성자   랑 검색어
        if(StringUtils.equals("itemNm",  searchBy) ){

            return QBoard.board.title.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("content",  searchBy) ){

            return QBoard.board.content.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("writer",  searchBy) ){

            return QBoard.board.writer.like("%" + searchQuery + "%");
        }

        return null;

    }



    @Override
    public Page<Board> getBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable) {

        QueryResults<Board> result =  jpaQueryFactory.selectFrom(QBoard.board)
                .where( regDtsAfter(boardSearchDTO.getSearchDateType()),
                        searchBoardCategory(boardSearchDTO.getBoardCategory()),
                        searchByLike( boardSearchDTO.getSearchBy(), boardSearchDTO.getSearchQuery() )
                )
                .orderBy(QBoard.board.id.desc() )
                .offset(pageable.getOffset())           // 몇번부터 1번글부터 // 11번글 부터
                .limit(pageable.getPageSize())          //size = 10 10개씩
                .fetchResults();

        List<Board> content = result.getResults();
        long total = result.getTotal();

        /////////////////////////////////////////////////////////////////////////



        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MainBoardDTO> getMainBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        QBoard board = QBoard.board;
        QBoardImg boardImg = QBoardImg.boardImg;

        QueryResults<MainBoardDTO> result =  jpaQueryFactory.select(
                        new QMainBoardDTO(
                                board.id,
                                board.writer,
                                board.title,
                                board.view,
                                board.regTime,
                                board.boardCategory
                        )
                )
                .from(boardImg)
                .join(boardImg.board, board)
                .where(boardImg.repimgYn.eq("Y"))    //대표이미지
                .orderBy(boardImg.id.desc() )
                .offset(pageable.getOffset())           // 몇번부터 1번글부터 // 11번글 부터
                .limit(pageable.getPageSize())          //size = 10 10개씩
                .fetchResults();

        List<MainBoardDTO> content = result.getResults();
        long total = result.getTotal();


        return new PageImpl<>(content, pageable, total);
    }
}
