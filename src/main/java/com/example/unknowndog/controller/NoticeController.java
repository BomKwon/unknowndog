package com.example.unknowndog.controller;

import com.example.unknowndog.dto.*;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.service.MainService;
import com.example.unknowndog.service.NoticeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final MainService mainService;

    @GetMapping({"/list", "/list/{page}"})
    public String noticeList(NoticeSearchDTO noticeSearchDTO, NoticeDTO noticeDTO,
                             @PathVariable("page") Optional<Integer> page, Model model,
                             Principal principal) {

        if (principal != null) {
            String nickname = mainService.getUserName(principal);
            model.addAttribute("nickname", nickname);
        }

        if (principal == null) {

            Pageable pageable = PageRequest
                    .of(page.isPresent() ? page.get() : 0 , 10);

            Page<Notice> notice = noticeService.getNoticePage(noticeSearchDTO, pageable);

            model.addAttribute("notice", notice);
            model.addAttribute("noticeDTO", noticeDTO);
            model.addAttribute("maxPage", 10);

            return "/notice/noticeList";

        }

        Pageable pageable = PageRequest
                .of(page.isPresent() ? page.get() : 0 , 10);

        Page<Notice> notice = noticeService.getNoticePage(noticeSearchDTO, pageable);

        model.addAttribute("notice", notice);
        model.addAttribute("noticeDTO", noticeDTO);
        model.addAttribute("maxPage", 10);

        return "/notice/noticeList";
    }

    @GetMapping("/read/{noticeId}")
    public String noticeRead(@PathVariable Long noticeId, Model model, Principal principal) {

        if (principal != null) {
            String nickname = mainService.getUserName(principal);
            model.addAttribute("nickname", nickname);
        }

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

        return "/notice/noticeRead";
    }









}


