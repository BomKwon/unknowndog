package com.example.unknowndog.controller;


import com.example.unknowndog.dto.MainQuestDTO;
import com.example.unknowndog.dto.QuestSearchDTO;
import com.example.unknowndog.dto.UserDTO;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.service.MainService;
import com.example.unknowndog.service.QuestService;
import com.example.unknowndog.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {



    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final QuestService questService;
    private final MainService mainService;




    @GetMapping("/register")
    public String userForm(Model model){      //object로 보내서 form에서 사용하려고


        model.addAttribute("userFormDTO", new UserDTO());

        return "/user/userForm";    //view > html

    }

    @PostMapping("/register")
    public String userForm(@Valid UserDTO userDTO,
                             BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "/user/userForm";    //view > html
        }

        try {
            User user = User
                    .createUser(userDTO, passwordEncoder);

            userService.saveUser(user);

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/userForm";    //view > html
        }


        return "redirect:/user/login";
    }



    //중복확인을 만들어보자
    @PostMapping("/duplicateE")
    public @ResponseBody String duplicateCheckE(String email){
        log.info(email);


        if (userService.validateDuplicateEmailonly(email) != null) {
            return "이미 사용 중인 이메일이개";
        }else {
            return "사용 가능한 이메일이개";
        }

    }

    @PostMapping("/duplicateN")
    public @ResponseBody String duplicateCheckN(String nickname){
        log.info(nickname);
        if (userService.validateDuplicateNnameonly(nickname) != null) {
            return "이미 사용 중인 별명이개";
        }else {
            return "사용 가능한 별명이개";
        }
    }


    @PostMapping("/passwordChk")
    public String passwordChk(String password, String password2){

        if (password == password2) {
            return "입력한 비밀번호가 똑같다개";
        } else {
            return "입력한 비밀번호를 확인하고 재입력해주개";
        }

    }



    @GetMapping("/login")
    public String loginUser(){

        return "/user/login";
    }
    @GetMapping("/login/error")
    public String loginError(Model model){

        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호가 틀렸다개!!!!");

        return "/user/login";
    }


    @GetMapping("/view/{email}")  //간단정보 회원끼리 누구나
    public String walkview(@PathVariable("email") String email, Model model, Principal principal,
                           QuestSearchDTO questSearchDTO, Pageable pageable){

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        try {
            UserDTO userDTO = UserDTO.of(userService.findByEmail(email));
            model.addAttribute("userDTO" , userDTO);


        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage",
                    "존재하지 않는 회원이개");
            model.addAttribute("userDTO", new UserDTO());

            return "redirect:/";

        }

        return "/user/userView";
    }



    //차후 수정사항
    //지금은 이메일만 받아오는데 추후엔 닉네임이나 이름으로 받아올 수 있도록 할 예정임
    //그리고 오류 터져서 회원번호(id)가 아닌 일단 이메일로 바꿔둠 추후 로그인한 이메일을 통해 회원번호와 별명을 가져올것
    @GetMapping("/read/{email}")  //자기만 볼수있는 세세한 정보
    public String userDtl(@PathVariable("email") String email
            , Model model, Principal principal){

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        try {
            UserDTO userDTO = UserDTO.of(userService.findByEmail(email));
            model.addAttribute("userDTO" , userDTO);

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage",
                    "존재하지 않는 회원이개");
            model.addAttribute("userDTO", new UserDTO());

            return "redirect:/";

        }

        return "/user/userRead";
    }




    @GetMapping("/modify/{email}")  //자기만 볼수있는 세세한 정보
    public String userModifyGet(@PathVariable("email") String email
            , Model model, Principal principal){

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        try {
            UserDTO userDTO = UserDTO.of(userService.findByEmail(email));
            model.addAttribute("userDTO" , userDTO);

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage",
                    "존재하지 않는 회원이개");
            model.addAttribute("userDTO", new UserDTO());

            return "redirect:/";

        }

        return "/user/userForm";
    }

    @PostMapping("/modify/{email}")
    public String modify(@Valid UserDTO userDTO,
                         BindingResult bindingResult,
                         Model model, Principal principal){

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        if (bindingResult.hasErrors()) {
            return "/user/userForm";
        }

        try {
            userService.modifyUser(userDTO);
        }catch (Exception e){
            model.addAttribute("errorMessage", "정보 수정 중 오류가 발생했개");
            return "/user/userRead";
        }

        return "redirect:/user/userRead";

    }



    @GetMapping("/read")        //   /user/3  3번이미지 보여줘
    public String userInfo(@PathVariable("email") String email, Long userId
            , Model model, Principal principal) {


        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);


//        try {
//
//
//
//            UserDTO userDTO = userService.getUserDtl(userId);
//
//
//            model.addAttribute("userDTO" , userDTO);
//            // html에서 thyleaf  th:object="${questFormDto}"
//
//        } catch (EntityNotFoundException e) {
//            model.addAttribute("errorMessage",
//                    "존재하지 않는 회원이개");
//
//            return "/quest/questList";
//
//        }

        return "/user/userView";

    }






    //// TODO: 2024-06-21 탈퇴 만드셈
    @GetMapping("/remove/{userId}")
    public @ResponseBody ResponseEntity userRemove(@PathVariable("userId") Long userId, RedirectAttributes redirectAttributes,
                                                   Principal principal, Model model){

        log.info("들어온 아이디 : " + userId);

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        String user = userService.remove(userId);
        redirectAttributes.addFlashAttribute("result", userId + "번 회원이 탈퇴했습니다.");

        return new ResponseEntity<String>(user, HttpStatus.OK);

    }




    //아이디 비번 찾기
    @GetMapping("/findUser")
    public /*@ResponseBody ResponseEntity */ String findUser(/*@PathVariable("email") */String emaill) {


        return "/404NotFound";

    }




}
