package com.example.unknowndog.controller;

import com.example.unknowndog.dto.*;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.service.BoardService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final NoticeService noticeService;
    private final MainService mainService;

    @GetMapping("/list")
    public String boardAll(BoardSearchDTO boardSearchDTO, BoardDTO boardDTO,
                           @PathVariable("page") Optional<Integer> page, Model model,
                           NoticeSearchDTO noticeSearchDTO, PageRequestDTO pageRequestDTO,
                           Principal principal){


        if (principal == null) {

            Pageable pageable = PageRequest
                    .of(page.isPresent() ? page.get() : 0 , 10);


            PageResponseDTO<BoardListReplyCountDTO> pageResponseDTO = boardService.listWithReplyCount(pageRequestDTO);

            Page<Board> boards = boardService.getboardPage(boardSearchDTO, pageable);

            model.addAttribute("boards", boards);
            model.addAttribute("boardDTO", boardDTO);
            model.addAttribute("maxPage", 10);
            model.addAttribute("pageResponseDTO", pageResponseDTO);  /*여기에 모든게 담겨서 이제 다른건 필요없음요*/


            //공지
            Pageable pageable1 = PageRequest
                    .of(page.isPresent() ? page.get() : 0 , 10);


            Page<Notice> notice = noticeService.getNoticePage(noticeSearchDTO, pageable1);

            model.addAttribute("notice", notice);
            model.addAttribute("maxPage", 10);


            return "/board/boardList";

        }

        if (principal != null) {
            String nickname = mainService.getUserName(principal);
            model.addAttribute("nickname", nickname);
        }


        PageResponseDTO<BoardListReplyCountDTO> pageResponseDTO = boardService.listWithReplyCount(pageRequestDTO);


        Pageable pageable = PageRequest
                .of(page.isPresent() ? page.get() : 0 , 10);

        Page<Board> boards = boardService.getboardPage(boardSearchDTO, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        model.addAttribute("maxPage", 10);

        //공지
        Pageable pageable1 = PageRequest
                .of(page.isPresent() ? page.get() : 0 , 10);

        Page<Notice> notice = noticeService.getNoticePage(noticeSearchDTO, pageable1);

        model.addAttribute("notice", notice);
        model.addAttribute("maxPage", 10);



        return "/board/boardList";
    }



    @PreAuthorize("isAuthenticated()")
    @GetMapping("/new")
    public String boardForm(Principal principal, Model model) {

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        model.addAttribute("boardDTO", new BoardDTO());

        return "/board/boardForm";
    }

    @PostMapping("/new")
    public String questForm(@Valid BoardDTO boardDTO, BindingResult bindingResult,
                            Principal principal, Model model
            , @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList) throws IOException {

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);


        if (bindingResult.hasErrors()) {

            log.info("에러 발생" + boardDTO);
            model.addAttribute("errorMessage", "에러났개");
            return "/board/boardForm";
        }
        if (boardImgFileList.get(0).isEmpty() && boardDTO.getId() == null) {
            //대표이미지는 꼭 삽입해야한다.
            model.addAttribute("errorMessage", "대표사진은 필수항목이개");
            return "/board/boardForm";
        }
        log.info("getBytes :"  + Arrays.toString(boardImgFileList.get(0).getBytes()));
        log.info("getContentType :"  + boardImgFileList.get(0).getContentType());
        log.info("getOriginalFilename :"  + boardImgFileList.get(0).getOriginalFilename());
        log.info("정상 값 :" +boardDTO);

        try {

            boardService.getUserName(boardDTO, principal);
            boardService.newboard(boardDTO, boardImgFileList);

        }catch (Exception e){

            log.info(e);

            model.addAttribute("errorMessage",
                    "업로드중 문제가 발생했다개!");
            return "/board/boardForm";

        }

        // 읽기 페이지로 보내야 하지 않을까?
        return "redirect:/board/list";
    }



    @GetMapping("/read/{boardId}")        //   /board/3  3번이미지 보여줘
    public String questDtl(@PathVariable("boardId") Long boardId
            , Model model, Principal principal, BoardDTO boardDTO) {

        if (principal != null) {
            String nickname = mainService.getUserName(principal);
            model.addAttribute("nickname", nickname);
        }


        try {

                boardService.updateViews(boardId);

            boardDTO = boardService.getboardDetail(boardId);


            model.addAttribute("boardDTO" , boardDTO);
            // html에서 thyleaf  th:object="${questFormDto}"

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage",
                    "존재하지 않는 의뢰다개");

            return "/board/boardList";

        }


        return "/board/boardRead";

    }


}
