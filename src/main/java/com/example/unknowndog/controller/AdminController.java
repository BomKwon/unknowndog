package com.example.unknowndog.controller;

import com.example.unknowndog.dto.UserSearchDTO;
import com.example.unknowndog.dto.NoticeDTO;
import com.example.unknowndog.dto.UserDTO;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.service.MainService;
import com.example.unknowndog.service.NoticeService;
import com.example.unknowndog.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    private final NoticeService noticeService;
    private final UserService userService;
    private final MainService mainService;

    @GetMapping("/")
    public String adminMain(Model model, Principal principal){

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        if(!principal.getName().equals("00bom00@naver.com")) {
            model.addAttribute("adminErr", "접속 권한이 없다개 메인화면으로 돌아간다개");
            return "redirect:/";
        }

        return "/admin/adminMain";
    }


    //글 작성
    @GetMapping("/notice/new")
    public String newNotice(Principal principal, Model model,
                            NoticeDTO noticeDTO, UserDTO userDTO){

        // TODO: 2024-06-21 알려주신 대로 했지만내가 멍청해서 적용을 못하기에 추후에 다시 보도록(작성자=닉네임 연결부분) 완

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        model.addAttribute("noticeDTO", new NoticeDTO());

        return "/admin/noticeForm";
    }

    @PostMapping("/notice/new")
    public String newNotice(@Valid NoticeDTO noticeDTO,
                            BindingResult bindingResult,
                            Model model, Principal principal) {

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);


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


    //회원리스트
    @GetMapping("/user/list")
    public String userList(UserSearchDTO userSearchDTO, UserDTO userDTO,
                           @PathVariable("page") Optional<Integer> page, Model model, Principal principal){

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        Pageable pageable = PageRequest
                .of(page.isPresent() ? page.get() : 0 , 10);

        Page<User> users = userService.getUserList(userSearchDTO, pageable);

        model.addAttribute("users", users);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("maxPage", 10);

        return "/admin/userList";
    }


    @GetMapping("/notice/{noticeId}")
    public String noticeMo(@PathVariable Long noticeId, Model model, Principal principal) {


        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);


        noticeService.updateViews(noticeId);

        try {
            NoticeDTO noticeDTO = noticeService.getNoticeDetail(noticeId);
            model.addAttribute("noticeDTO" , noticeDTO);
            // html에서 thyleaf  th:object="${questFormDto}"

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage",
                    "존재하지 않는 공지다개");

            return "/notice/noticeList";

        }

        return "/notice/noticeForm";
    }



    @PostMapping("/notice/{noticeId}")
    public String itemUpdate(@Valid NoticeDTO noticeDTO,
                             BindingResult bindingResult,
                             Model model, Principal principal) {

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);


        if (bindingResult.hasErrors()) {
            return "/notice/noticeForm";
        }

        try {
            noticeService.updateNotice(noticeDTO);
        }catch (Exception e){
            model.addAttribute("errorMessage", "공지 수정 중 에러가 발생하였다개");
            return "/notice/noticeForm";
        }

        model.addAttribute("result", "공지 수정이 완료되었다개");

        return "redirect:/notice/read/{noticeId}";

    }


}
