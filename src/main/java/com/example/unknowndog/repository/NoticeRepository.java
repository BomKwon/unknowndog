package com.example.unknowndog.repository;

import com.example.unknowndog.dto.NoticeDTO;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.repository.search.NoticeRepositoryCustorm;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface NoticeRepository extends JpaRepository<Notice, Long>, QuerydslPredicateExecutor<Notice>, NoticeRepositoryCustorm  {

    @Modifying
    @Query("update Notice n set n.view = n.view + 1 where n.id = :id")
    int updateViews(@Param("id") Long id);

}
