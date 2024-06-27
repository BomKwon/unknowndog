package com.example.unknowndog.repository;

import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.repository.search.NoticeRepositoryCustorm;
import com.example.unknowndog.repository.search.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, UserRepositoryCustom {

    //이메일로 정보 찾기 //중복회원?? 혹은 로그인시?
    User findByEmail (String email);

    User findByNickname (String nickname);

}
