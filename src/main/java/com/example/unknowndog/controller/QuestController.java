package com.example.unknowndog.controller;

import com.example.unknowndog.dto.MainQuestDTO;
import com.example.unknowndog.dto.QuestFormDTO;
import com.example.unknowndog.dto.QuestSearchDTO;
import com.example.unknowndog.entity.Quest;
import com.example.unknowndog.service.QuestService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/quest")
public class QuestController {

    private final QuestService questService;


    @GetMapping("/new")
    public String questForm(Principal principal, Model model) {

        model.addAttribute("questFormDTO", new QuestFormDTO());

        return "/quest/questForm";
    }

    @PostMapping("/new")
    public String questForm(@Valid QuestFormDTO questFormDTO, BindingResult bindingResult,
                            Principal principal, Model model
            , @RequestParam("questImgFile") List<MultipartFile> questImgFileList) throws IOException {



        if (bindingResult.hasErrors()) {

            log.info("에러 발생" + questFormDTO);
            model.addAttribute("errorMessage", "에러났개");
            return "/quest/questForm";
        }
        if (questImgFileList.get(0).isEmpty() && questFormDTO.getId() == null) {
            //대표이미지는 꼭 삽입해야한다.
            model.addAttribute("errorMessage", "대표사진은 필수항목이개");
            return "/quest/questForm";
        }
        log.info("getBytes :"  + Arrays.toString(questImgFileList.get(0).getBytes()));
        log.info("getContentType :"  + questImgFileList.get(0).getContentType());
        log.info("getOriginalFilename :"  + questImgFileList.get(0).getOriginalFilename());
        log.info("정상 값 :" +questFormDTO);

        try {

            questService.getUserName(questFormDTO, principal);
            questService.saveQuest(questFormDTO, questImgFileList);

        }catch (Exception e){

            log.info(e);

            model.addAttribute("errorMessage",
                    "업로드중 문제가 발생했다개!");
            return "/quest/questForm";
        }


        // 읽기 페이지로 보내야 하지 않을까?
        return "redirect:/quest/list";
    }



    @GetMapping("/read/{questId}")        //   /quest/3  3번이미지 보여줘
    public String questDtl(@PathVariable("questId") Long questId
            , Model model) {


        try {

            questService.updateViews(questId);

            QuestFormDTO questFormDTO = questService.getQuestDetail(questId);
            model.addAttribute("questFormDTO" , questFormDTO);
            // html에서 thyleaf  th:object="${questFormDto}"

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage",
                    "존재하지 않는 의뢰다개");

            return "/quest/questForm";

        }

        return "/quest/questRead";

    }

    @GetMapping("/modify/{questId}")        //   /quest/3  3번이미지 보여줘
    public String questModifyGet(@PathVariable("questId") Long questId
            , Model model) {


        try {

            QuestFormDTO questFormDTO = questService.getQuestDetail(questId);
            model.addAttribute("questFormDTO" , questFormDTO);
            // html에서 thyleaf  th:object="${questFormDto}"

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage",
                    "존재하지 않는 의뢰다개");

            return "/quest/questForm";

        }

        return "/quest/questForm";

    }

    @PostMapping("/modify/{questId}")
    public String questUpdate(@Valid QuestFormDTO questFormDTO,
                             BindingResult bindingResult,
                             @RequestParam("questImgFile") List<MultipartFile> multipartFiles,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "/quest/questForm";
        }

        if(multipartFiles.get(0).isEmpty() && questFormDTO.getId() == null){
            model.addAttribute("errorMessage", "대표 이미지는 필수 입력값이다개");
            return "/quest/questForm";
        }

        try {
            questService.updateQuest(questFormDTO, multipartFiles);
        }catch (Exception e){
            model.addAttribute("errorMessage", "수정 중에 문제가 발생했다개");
            return "/quest/questForm";
        }
        return "redirect:/";

    }


    // TODO: 2024-06-21 글 삭제 작동 안되니까 수정하셈 케스케이트 사용해라


    @DeleteMapping("/remove/{questId}")
    public String questRemove(Long questId, RedirectAttributes redirectAttributes){

        String quest = questService.remove(questId);
        redirectAttributes.addFlashAttribute("result", questId + "번 글이 삭제됐다개");

        return "redirect:/quest/List";

    }






    @GetMapping({"/list","/list/{page}"})
    public String questList(QuestSearchDTO questSearchDTO,
                            @PathVariable("page") Optional<Integer> page, Model model) {

        // TODO: 2024-06-21 밑에 코드로 안돼서 급한대로 메인은 되니까 붙여놓음

//        Pageable pageable = PageRequest
//                .of(page.isPresent() ? page.get() : 0 , 5);
//
//        Page<Quest> quests = questService
//                .getQuestPage(questSearchDTO, pageable);
//
//        quests.forEach(quest -> log.info(quest));
//
//        model.addAttribute("quests", quests);
//        model.addAttribute("questSearchDTO", questSearchDTO);

        Pageable pageable = PageRequest
                .of(page.isPresent() ? page.get() : 0 , 10);

        Page<MainQuestDTO> quests = questService
                .getMainQuestPage(questSearchDTO, pageable);

        quests.forEach(quest -> log.info(quest));

        model.addAttribute("quests", quests);
        model.addAttribute("questSearchDTO", questSearchDTO);

        model.addAttribute("maxPage", 10);



        return "/quest/questList";
    }




}
