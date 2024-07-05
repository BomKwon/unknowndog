package com.example.unknowndog.dto;

import com.example.unknowndog.constant.BoardCategory;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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
