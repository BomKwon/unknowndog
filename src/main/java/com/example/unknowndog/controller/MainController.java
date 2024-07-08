package com.example.unknowndog.controller;


import com.example.unknowndog.dto.*;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    private final MainService mainService;


//    @GetMapping("/admin")
//    public String adminMain(Model model, Principal principal) {
//
//        if () {
//            model.addAttribute("adminErr", "접속 권한이 없다개 메인화면으로 돌아간다개");
//
//            return "main";
//        }
//
//        return "main";
//    }


    @GetMapping(value = {"/", "/{page}"})
    public String main(UserDTO userDTO, Principal principal, Model model,
                       QuestSearchDTO questSearchDTO,
                       @PathVariable Optional<Integer> page,
                       BoardSearchDTO boardSearchDTO, BoardDTO boardDTO,
                       QuestFormDTO questFormDTO,
                       NoticeSearchDTO noticeSearchDTO, NoticeDTO noticeDTO,
                       PageRequestDTO pageRequestDTO) {


        if (principal != null) {

            String nickname = mainService.getUserName(principal);
            model.addAttribute("nickname", nickname);
            model.addAttribute("userDTO", userDTO);

        }

        //산책하개 최신 5글만 보이게 하는

        /*의뢰*/
        Pageable pageable = PageRequest
                .of(page.isPresent() ? page.get() : 0 , 5);

        Page<MainQuestDTO> quests = questService
                .getMainQuestPage(questSearchDTO, pageable);

//        quests.forEach(quest -> log.info(quest));

        model.addAttribute("quests", quests);
        model.addAttribute("questFormDTO", questFormDTO);
        model.addAttribute("questSearchDTO", questSearchDTO);


        /*공지*/
        Page<Notice> notice = noticeService.getNoticePage(noticeSearchDTO, pageable);

        model.addAttribute("notice", notice);
        model.addAttribute("noticeDTO", noticeDTO);


        /*자유게시판*/
        PageResponseDTO<BoardListReplyCountDTO> pageResponseDTO = boardService.listWithReplyCountMain(pageRequestDTO);

        Page<Board> boards = boardService.getboardPage(boardSearchDTO, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);



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


    @GetMapping("/404_NotFound")
    public String notFound(){
        return "/404NotFound";
    }


}
