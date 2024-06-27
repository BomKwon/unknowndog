package com.example.unknowndog.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(exclude = "board")
//@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {


  private Long replyId;

  private Long boardId;
//  private Board board; //참조대상

  private String replyText; // 내용

  private String replyer; //작성자

  private String createBy;

  private LocalDateTime regTime;

  private LocalDateTime updateTime;



}
