package com.example.unknowndog.service;

import com.example.unknowndog.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MainService {

    private final UserService userService;

    //회원의 닉네임을 가져오는것인데..
    public String getUserName(Principal principal){

        String email = principal.getName();
        User user = userService.findByEmail(email);

        String userName = user.getNickname();

        return userName;
    }

}
