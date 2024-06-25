package com.example.unknowndog.controller;

import com.example.unknowndog.dto.NoticeDTO;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.service.NoticeService;
import com.example.unknowndog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/list")
    public String boardAll(){
        return "/board/boardList";
    }

//    @PostMapping("/new")
//    public String newBoard(NoticeDTO noticeDTO, Principal principal) {
//
//        //일단 보드디티오가 없어서 노티스디티오를 넣어두고 이건 작성자 없이 가져온다는 가정하에 쓸것
//
//        String email = principal.getName();
//        noticeDTO.setWriter(email); //일단 노티스에 넣어둠
//        //User user = userService.findByEmail(email);
//        //noticeDTO.setWriter(user.getName()); 이렇게 안됨~ dto에 저장을 한다해도 modelmapper로는 사용이 안됨
//        //서비스 들어가서 해야됨
//
//        //NoticeService.newNotice()
//    }

}
