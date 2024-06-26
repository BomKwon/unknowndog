package com.example.unknowndog.controller;


import com.example.unknowndog.dto.*;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.Quest;
import com.example.unknowndog.service.BoardService;
import com.example.unknowndog.service.NoticeService;
import com.example.unknowndog.service.QuestService;
import com.example.unknowndog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MainController {


    private final UserService userService;
    private final QuestService questService;
    private final NoticeService noticeService;
    private final BoardService boardService;

    @GetMapping(value = {"/", "/{page}"})
    public String main(UserDTO userDTO, Principal principal, Model model,
                       QuestSearchDTO questSearchDTO,
                       @PathVariable Optional<Integer> page,
                       BoardSearchDTO boardSearchDTO, BoardDTO boardDTO,
                       QuestFormDTO questFormDTO,
                       NoticeSearchDTO noticeSearchDTO, NoticeDTO noticeDTO) {

        if (principal == null){  //비회원도 봐야하니까

            /*의뢰*/
            Pageable pageable = PageRequest
                    .of(page.isPresent() ? page.get() : 0 , 5);

            Page<MainQuestDTO> quests = questService
                    .getMainQuestPage(questSearchDTO, pageable);

            quests.forEach(quest -> log.info(quest));

            model.addAttribute("quests", quests);
            model.addAttribute("questSearchDTO", questSearchDTO);

            /*공지*/
            Page<Notice> notice = noticeService.getNoticePage(noticeSearchDTO, pageable);

            model.addAttribute("notice", notice);
            model.addAttribute("noticeDTO", noticeDTO);

            /*자유게시판*/
            Page<Board> boards = boardService.getboardPage(boardSearchDTO, pageable);

            model.addAttribute("boards", boards);
            model.addAttribute("boardDTO", boardDTO);


            return "main";
        }

        userDTO = UserDTO.of(userService.findByEmail(principal.getName()));

        log.info(userDTO.getId());

        model.addAttribute("un", principal.getName());
        model.addAttribute("userDTO", userDTO);


        //산책하개 최신 5글만 보이게 하는

        /*의뢰*/
        Pageable pageable = PageRequest
                .of(page.isPresent() ? page.get() : 0 , 5);

        Page<MainQuestDTO> quests = questService
                .getMainQuestPage(questSearchDTO, pageable);

        quests.forEach(quest -> log.info(quest));

        model.addAttribute("quests", quests);
        model.addAttribute("questSearchDTO", questSearchDTO);


        /*공지*/
        Page<Notice> notice = noticeService.getNoticePage(noticeSearchDTO, pageable);

        model.addAttribute("notice", notice);
        model.addAttribute("noticeDTO", noticeDTO);


        /*자유게시판*/
        Page<Board> boards = boardService.getboardPage(boardSearchDTO, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("boardDTO", boardDTO);


        return "main";
    }


//    @GetMapping(value = {"/", "/{page}"})
//    public String main(QuestSearchDTO questSearchDTO,
//                       @PathVariable Optional<Integer> page,
//                       Model model) {
//
//        Pageable pageable = PageRequest
//                .of(page.isPresent() ? page.get() : 0 , 5);
//
//        // 메인페이지에서 파라미터나 검색을 위한 어떠한 특정값을 받는다.
//        // 그 값을 통해서 데이터베이스에서 정해진 조건으로 값을 가져온다.
//        Page<MainQuestDTO> quests = QuestService
//                .getMainQuestPage(questSearchDTO, pageable);
//
//        model.addAttribute("quests", quests);
//        model.addAttribute("questSearchDTO", questSearchDTO);
//        model.addAttribute("maxPage", 5);
//
//
//        return "main";
//    }



}
