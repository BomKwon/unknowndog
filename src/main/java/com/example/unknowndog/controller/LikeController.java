package com.example.unknowndog.controller;

import com.example.unknowndog.dto.LikeDetailDTO;
import com.example.unknowndog.dto.LikeQuestDTO;
import com.example.unknowndog.dto.MainQuestDTO;
import com.example.unknowndog.dto.QuestSearchDTO;
import com.example.unknowndog.service.LikeService;
import com.example.unknowndog.service.MainService;
import com.example.unknowndog.service.QuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping
public class LikeController {


    //    @GetMapping("/")
//    public String likesList(Principal principal, Model model){
//
//        String nickname = mainService.getUserName(principal);
//        model.addAttribute("nickname", nickname);
//
//        if (principal == null) {
//
//            return "/like/like";
//
//        }
//
//        return "/like/like";
//    }

    private final MainService mainService;
    private final LikeService likeService;

    @PostMapping("/likes")
    public @ResponseBody ResponseEntity order(
            @RequestBody @Valid LikeQuestDTO likeQuestDTO,
            BindingResult bindingResult, Principal principal, Model model) {

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        System.out.println("입력받은 의뢰아이디" + likeQuestDTO.getQuestId());
        System.out.println("입력받은 의뢰수량" + likeQuestDTO.getCount());  /*숨겨진 셀렉트박스로 1고정*/

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }//Valid수행하여 조건에 부합하지 않을경우 에러 필드와
            //에러메시지가 담긴다. dto 에 선언해놓음

            return new ResponseEntity<String>
                    (sb.toString(), HttpStatus.BAD_REQUEST);

        }

        String email = principal.getName(); //로그인한 사람의 이메일
        Long likeQuestId;

        // 서비스를 통해서 카트에 아이템을 담는다. addCart(likeQuestDTO, email);
        // likeQuestDTO에 itemId로 아이템을 findbyID로 객체를 가져와서 참조하도록

        try {
            likeQuestId = likeService.addLike(likeQuestDTO, email);

        }catch (Exception e){
            return new ResponseEntity<String>
                    (e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>( likeQuestId, HttpStatus.OK);

    }



    @GetMapping("/likes")
    public String questHist(Principal principal, Model model) {

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        List<LikeDetailDTO> likeDetailDTOList
                = likeService.getLikeList(principal.getName());
        // email을 파라미터로 넘긴다.

        log.info(likeDetailDTOList);

        model.addAttribute("likeDetailDTOList", likeDetailDTOList);



        return "/like/likeList";
    }



    @DeleteMapping("/likeQuest/{likeQuestId}")
    public @ResponseBody ResponseEntity deleteLikeQuest(
            @PathVariable("likeQuestId") Long likeQuestId,
            Principal principal){

        log.info("들어온 아이디: "+ likeQuestId);

//        System.out.println("넘어온 likeQuestId : " + likeQuestId);
        //넘어온값이 확인되면 저 값을 이용해서
        //아까 확인한대로 service에서 id를 넘겨주고 그 아이디를 통해서
        // repository로 id를 넘겨서 deletebyid(Long id) 메소드를 통해서 삭제
        // id를 넘겨서 entity를 찾아오고 예외처리한후 entity를 가지고 delete(entity)수행
        if (principal != null) {
            System.out.println("로그인한사람 : " + principal.getName());

        }       // service.validate 에서 이메일과 카트의 상품의 만든이의 t/f 관계 반대
        if(!likeService.validatelikeQuest(likeQuestId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        String likeQuestNm = likeService.deletelikeQuest(likeQuestId);


        //return new ResponseEntity<Long>(likeQuestId, HttpStatus.OK);
        return new ResponseEntity<String>(likeQuestNm, HttpStatus.OK);
    }


    //"/likeQuest/" + likeQuestId  + "?count=" + count;
    @PutMapping("/likeQuest/{likeQuestId}")
    public @ResponseBody ResponseEntity updateLikeQuest(
            @PathVariable("likeQuestId") Long likeQuestId,
            int count, Principal principal){

        //url로 받은 파라미터 likeQuestId와
        //쿼리스트링으로 받은 count를 이용하여
        // db에서 likeQuest을 찾아서 수량을 변경 수행

        if(count <= 0){
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        }else if( !likeService.validatelikeQuest(likeQuestId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(likeQuestId, HttpStatus.OK);
    }





}
