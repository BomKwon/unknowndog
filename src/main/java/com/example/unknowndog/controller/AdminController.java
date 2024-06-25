package com.example.unknowndog.controller;

import com.example.unknowndog.dto.NoticeDTO;
import com.example.unknowndog.dto.UserDTO;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.service.NoticeService;
import com.example.unknowndog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    private final NoticeService noticeService;
    private final UserService userService;

    //글 작성
    @GetMapping("/notice/new")
    public String newNotice(Principal principal, Model model,
                            NoticeDTO noticeDTO, UserDTO userDTO){

        // TODO: 2024-06-21 알려주신 대로 했지만내가 멍청해서 적용을 못하기에 추후에 다시 보도록(작성자=닉네임 연결부분) 완

        model.addAttribute("noticeDTO", new NoticeDTO());

        return "/admin/noticeForm";
    }

    @PostMapping("/notice/new")
    public String newNotice(@Valid NoticeDTO noticeDTO,
                            BindingResult bindingResult,
                            Model model, Principal principal) {

        Notice notice = noticeDTO.newNotice();

        if (bindingResult.hasErrors()) {
            log.info("에러발생했개! "+ noticeDTO);
            model.addAttribute("errMsg", "에러발생했개");
            return "/admin/noticeForm";
        }

        try {
            noticeService.getUserName(noticeDTO, principal);
            noticeService.newNotice(noticeDTO, principal);

        }catch (Exception e){

            log.info(e);

            model.addAttribute("errorMessage",
                    "업로드중 문제가 발생했다개!");
            return "/admin/noticeForm";
        }




        // 읽기 페이지로 보내야 하지 않을까?
        return "redirect:/notice/list";

    }

}
