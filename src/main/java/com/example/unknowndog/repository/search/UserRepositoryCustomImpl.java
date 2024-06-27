package com.example.unknowndog.repository.search;

import com.example.unknowndog.dto.UserSearchDTO;
import com.example.unknowndog.entity.QUser;
import com.example.unknowndog.entity.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private JPAQueryFactory jpaQueryFactory;

    public UserRepositoryCustomImpl(EntityManager em){

        this.jpaQueryFactory = new JPAQueryFactory(em);
    }


    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        // 이메일 , 별명 , 이름   랑 검색어
        if(StringUtils.equals("email",  searchBy) ){

            return QUser.user.email.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("nickname",  searchBy) ){

            return QUser.user.nickname.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("name",  searchBy) ){

            return QUser.user.name.like("%" + searchQuery + "%");
        }

        return null;

    }

    @Override
    public Page<User> getUserPage(UserSearchDTO userSearchDTO, Pageable pageable) {
        QueryResults<User> result =  jpaQueryFactory.selectFrom(QUser.user)
                .where( searchByLike( userSearchDTO.getSearchBy(),
                                userSearchDTO.getSearchQuery() )
                )
                .orderBy(QUser.user.id.desc() )
                .offset(pageable.getOffset())           // 몇번부터 1번글부터 // 11번글 부터
                .limit(pageable.getPageSize())          //size = 10 10개씩
                .fetchResults();

        List<User> content = result.getResults();
        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

}
