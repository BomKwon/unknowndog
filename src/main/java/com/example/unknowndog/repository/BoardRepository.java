package com.example.unknowndog.repository;

import com.example.unknowndog.entity.Board;
import com.example.unknowndog.repository.search.BoardRepositoryCustorm;
import com.example.unknowndog.repository.search.BoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> , QuerydslPredicateExecutor<Board>, BoardRepositoryCustorm, BoardSearch {

    // save, deletebyid, findbyid

    // select * from item where itemnm = #{param1} or itemdetail = #{param2}
    //쿼리 메소드 : 메소드명이 곧 쿼리문

    @Modifying  //조회수
    @Query("update Board b set b.view = b.view + 1 where b.id = :id")
    int updateViews(@Param("id") Long id);


    List<Board> findByTitleOrBoardDetail(String title, String content);

    @Query("select b from Board b where b.title = :title or b.content = :content")
    List<Board> findBybTitleOrbDetail (@Param("title") String title,@Param("BoardDetail") String content);

    @Query( "select i from Board i where i.content like %:content% order by i.writer desc ")
    List<Board> findbyBoarddeta (@Param("BoardDetail") String content );

    @Query( value = "select * from Board b where b.content like %:content% order by b.writer desc ", nativeQuery = true )
    List<Board> findbyBoarddetaNative (@Param("BoardDetail") String content );






















}
