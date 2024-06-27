package com.example.unknowndog.controller;


import com.example.unknowndog.dto.PageRequestDTO;
import com.example.unknowndog.dto.PageResponseDTO;
import com.example.unknowndog.dto.ReplyDTO;
import com.example.unknowndog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/replies/")
public class ReplyController {

  private final ReplyService replyService;

  @PostMapping(value = "/new")
  public Map<String, String> sin(@RequestBody ReplyDTO replyDTO , Principal principal) {

    if (principal == null) {
      Map<String, String> result = new HashMap<>();
      result.put("replyId", "로그인해라");
      return result;
    }

    log.info(replyDTO);

    replyDTO.setReplyer(principal.getName());

    Long replyId = replyService.register(replyDTO);

    Map<String, String> result = new HashMap<>();
    result.put("replyId", Long.toString(replyId));

    return result;

  }

  //읽기       //게시물읽기페이지에서

  @GetMapping(value = "/list/{boardId}")
  public PageResponseDTO<ReplyDTO> getList(@PathVariable("boardId") Long boardId,
                                           PageRequestDTO pageRequestDTO){

    log.info("현재 본문은 : " + boardId);
    log.info(pageRequestDTO);

   PageResponseDTO<ReplyDTO> pageResponseDTO =
           replyService.getListOfBoard(boardId, pageRequestDTO);

   return pageResponseDTO;

  }

  @GetMapping("/get/{replyId}")
  public ReplyDTO getReplyDTO(@PathVariable("replyId") Long replyId){

    return replyService.read(replyId);


  }

  @PutMapping("/modify/{replyId}")
  public Map<String, Long> modify(@PathVariable("replyId") Long replyId,
                                  @RequestBody ReplyDTO replyDTO){

    replyDTO.setReplyId(replyId);

    replyService.modify(replyDTO);

    Map<String, Long> map = new HashMap<>();
    map.put("replyId", replyId);

    return map;

  }

  @DeleteMapping("/remove/{replyId}")
  public Map<String, Long> remove(@PathVariable("replyId") Long replyId){

    replyService.remove(replyId);

    Map<String, Long> map = new HashMap<>();
    map.put("replyId", replyId);

    return map;

  }


}
