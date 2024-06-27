package com.example.unknowndog.service;


import com.example.unknowndog.dto.PageRequestDTO;
import com.example.unknowndog.dto.PageResponseDTO;
import com.example.unknowndog.dto.ReplyDTO;

public interface ReplyService {


  public Long register(ReplyDTO replyDTO);

  PageResponseDTO<ReplyDTO> getListOfBoard(Long boardId, PageRequestDTO pageRequestDTO);

//  public List<Board> select();

//  //페이징처리, 검색처리, 목록

//  //페이징처리, 검색처리, 댓글수량, 목록

  public ReplyDTO read(Long replyId);

  public void modify(ReplyDTO replyDTO);

  public void remove(Long replyId);



}
