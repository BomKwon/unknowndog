package com.example.unknowndog.controller;

import com.example.unknowndog.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final MainService mainService;

    @GetMapping("/")
    public String orderForm(Principal principal, Model model){

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        return "/order/orderForm";
    }

}
