package com.example.unknowndog.repository;

import com.example.unknowndog.entity.Quest;
import com.example.unknowndog.repository.search.QuestRepositoryCustorm;
import com.example.unknowndog.repository.search.QuestSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> , QuerydslPredicateExecutor<Quest>, QuestRepositoryCustorm, QuestSearch {

    // save, deletebyid, findbyid

    // select * from item where itemnm = #{param1} or itemdetail = #{param2}
    //쿼리 메소드 : 메소드명이 곧 쿼리문

    @Modifying
    @Query("update Quest q set q.view = q.view + 1 where q.id = :id")
    int updateViews(@Param("id") Long id);


    List<Quest> findByTitleOrQuestDetail(String title, String questDetail);

    @Query("select q from Quest q where q.title = :title or q.questDetail = :questDetail")
    List<Quest> findByQTitleOrQDetail (@Param("title") String title,@Param("questDetail") String questDetail);

    List<Quest> findBySalaryLessThan (Integer salary);     // 급여 검색할때 사용
    List<Quest> findBySalaryGreaterThan(Integer salary);   //급여가 입력보다 큰거

    List<Quest> findBySalaryLessThanOrderBySalaryDesc (Integer salary); //내림차순

    @Query( "select i from Quest i where i.questDetail like %:questDetail% order by i.salary desc ")
    List<Quest> findbyItemdeta (@Param("questDetail") String questDetail );

    @Query( value = "select * from Quest q where q.questDetail like %:questDetail% order by q.salary desc ", nativeQuery = true)
    List<Quest> findbyItemdetaNative (@Param("questDetail") String questDetail );


    //제품 제목으로 검색
    //제품 제목으로 검색어가 포함되는거
    // 제품 내용으로 검색
    // 제품 내용으로 검색 포함
    // 제품 내용과 제목  으로 검색
    // 제품 내용과 제목 검색어 포함으로 검색
    // 제품 가격대별 검색 3단계
    // 상품 판매중인것만 / 상품 미판매중인것만
    // 검색으로 검색시 제품 판매중인것만
    // 검색으로 검색시 제품 판매 미판매 둘다
    // 수량이 몇개 이상인거

    //UML 다이어그램중 usecase 다이어그램으로 프로젝트 구상
    // 그에 맞는 ER다이어그램 작성  -> 그걸 통해서 테이블 생성

    //동적 쿼리




















}
