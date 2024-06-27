package com.example.unknowndog.repository.search;

import com.example.unknowndog.dto.UserSearchDTO;
import com.example.unknowndog.entity.QUser;
import com.example.unknowndog.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class UserSearchImpl extends QuerydslRepositorySupport implements UserSearch {


    public UserSearchImpl() {
        super(User.class);
    }


    @Override
    public Page<User> jpqlQuerygetUserPage(UserSearchDTO userSearchDTO, Pageable pageable) {
        QUser user = QUser.user; //q 도메인 객체 entity를 q로 바꾼것
        JPQLQuery<User> query = from(user);

        BooleanBuilder booleanBuilder =new BooleanBuilder();
        LocalDateTime localDateTime = LocalDateTime.now();// 현재

        if(userSearchDTO.getRole() != null){

            booleanBuilder.and(user.role.eq(userSearchDTO.getRole()));

        }


        if(StringUtils.equals("email", userSearchDTO.getSearchBy())){
            booleanBuilder.and(user.email.like("%" + userSearchDTO.getSearchQuery()+ "%"));

        }else if(StringUtils.equals("nickname",  userSearchDTO.getSearchBy()) ){

            booleanBuilder.and(user.nickname.like("%" + userSearchDTO.getSearchQuery()+ "%"));

        }else if(StringUtils.equals("name",  userSearchDTO.getSearchBy()) ){

            booleanBuilder.and(user.name.like("%" + userSearchDTO.getSearchQuery()+ "%"));

        }

        query.where(booleanBuilder);
        System.out.println("검색조건 추가 : " + query);


        query.where(user.id.gt(0L));
        System.out.println("0보다 큰 조건 id가 " + query);

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<User> content = query.fetch(); //실행
//    userList.forEach(user1 -> log.info(user1));
        long count = query.fetchCount(); //row 수
//    System.out.println(count);


        return new PageImpl<>(content, pageable, count);
    }
}
