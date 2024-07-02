package com.example.unknowndog.dto;

import com.example.unknowndog.constant.BoardCategory;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {

  private Long id;

  private String title;
  private String writer;

  private int view;

  private BoardCategory boardCategory;

  private LocalDateTime regTime;

  private LocalDateTime updateTime;

  private Long replyCount; //댓글 총수


}
