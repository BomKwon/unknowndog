package com.example.unknowndog.repository;

import com.example.unknowndog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //이메일로 정보 찾기 //중복회원?? 혹은 로그인시?
    User findByEmail (String email);

    User findByNickname (String nickname);

}
