package com.example.unknowndog.repository.search;

import com.example.unknowndog.dto.BoardSearchDTO;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> jpqlQuerygetBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        QBoard board = QBoard.board; //q 도메인 객체 entity를 q로 바꾼것
        JPQLQuery<Board> query = from(board);

        BooleanBuilder booleanBuilder =new BooleanBuilder();
        LocalDateTime localDateTime = LocalDateTime.now();// 현재

        if(boardSearchDTO.getBoardCategory() != null){

            booleanBuilder.and(board.boardCategory.eq(boardSearchDTO.getBoardCategory()));

        }

        if(StringUtils.equals("all", boardSearchDTO.getSearchDateType())  || boardSearchDTO.getSearchDateType() == null){

        }else {
            if(StringUtils.equals("1d", boardSearchDTO.getSearchDateType())){
                localDateTime = localDateTime.minusDays(1);
            }else if(StringUtils.equals("1w", boardSearchDTO.getSearchDateType())){
                localDateTime = localDateTime.minusWeeks(1);
            }else if(StringUtils.equals("1m", boardSearchDTO.getSearchDateType())){
                localDateTime = localDateTime.minusMonths(1);
            }else if(StringUtils.equals("6m", boardSearchDTO.getSearchDateType())){
                localDateTime = localDateTime.minusMonths(6);
            }

            booleanBuilder.and(board.regTime.after(localDateTime));
        }

        if(StringUtils.equals("title", boardSearchDTO.getSearchBy())){
            booleanBuilder.and(board.title.like("%" + boardSearchDTO.getSearchQuery()+ "%"));

        }else if(StringUtils.equals("content",  boardSearchDTO.getSearchBy()) ){

            booleanBuilder.and(board.content.like("%" + boardSearchDTO.getSearchQuery()+ "%"));

        }else if(StringUtils.equals("writer",  boardSearchDTO.getSearchBy()) ){

            booleanBuilder.and(board.writer.like("%" + boardSearchDTO.getSearchQuery()+ "%"));

        }

        query.where(booleanBuilder);
        System.out.println("검색조건 추가 : " + query);


        query.where(board.id.gt(0L));
        System.out.println("0보다 큰 조건 id가 " + query);

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> content = query.fetch(); //실행
//    boardList.forEach(board1 -> log.info(board1));
        long count = query.fetchCount(); //row 수
//    System.out.println(count);


        return new PageImpl<>(content, pageable, count);
    }


}
