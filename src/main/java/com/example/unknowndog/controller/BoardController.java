package com.example.unknowndog.controller;

import com.example.unknowndog.dto.*;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.User;
import com.example.unknowndog.service.BoardService;
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

    @GetMapping("/list")
    public String boardAll(BoardSearchDTO boardSearchDTO, BoardDTO boardDTO,
                           @PathVariable("page") Optional<Integer> page, Model model,
                           Principal principal){

        if (principal == null) {

            Pageable pageable = PageRequest
                    .of(page.isPresent() ? page.get() : 0 , 10);

            Page<Board> boards = boardService.getboardPage(boardSearchDTO, pageable);

            model.addAttribute("boards", boards);
            model.addAttribute("boardDTO", boardDTO);
            model.addAttribute("maxPage", 10);

            return "/board/boardList";

        }

        Pageable pageable = PageRequest
                .of(page.isPresent() ? page.get() : 0 , 10);

        Page<Board> boards = boardService.getboardPage(boardSearchDTO, pageable);

        model.addAttribute("boards", boards);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("maxPage", 10);

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


    @GetMapping("/new")
    public String boardForm(Principal principal, Model model) {

        model.addAttribute("boardDTO", new BoardDTO());

        return "/board/boardForm";
    }

    @PostMapping("/new")
    public String questForm(@Valid BoardDTO boardDTO, BindingResult bindingResult,
                            Principal principal, Model model
            , @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList) throws IOException {



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



    @GetMapping("/read/{boardId}")        //   /quest/3  3번이미지 보여줘
    public String questDtl(@PathVariable("boardId") Long boardId
            , Model model) {


        try {

            boardService.updateViews(boardId);

            BoardDTO boardDTO = boardService.getboardDetail(boardId);
            model.addAttribute("boardDTO" , boardDTO);
            // html에서 thyleaf  th:object="${questFormDto}"

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage",
                    "존재하지 않는 의뢰다개");

            return "/board/boardRead";

        }

        return "/board/boardRead";

    }


}
