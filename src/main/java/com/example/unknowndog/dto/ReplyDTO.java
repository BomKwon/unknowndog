package com.example.unknowndog.dto;

import com.example.unknowndog.entity.Order;
import com.example.unknowndog.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString(exclude = "board")
//@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {


  private Long id;

  private Long boardId;
//  private Board board; //참조대상

  private String replyText; // 내용

  private String replyer; //작성자

  private String createBy;

  private LocalDateTime regTime;



  private LocalDateTime updateTime;


  public ReplyDTO(Reply reply){

    this.id = reply.getId();
    //this.OrderDate = order.getOrderDate();  //String타입으로 변환을 위해서
    // format 적용
    this.regTime = LocalDateTime.parse(reply.getRegTime()
            .format(  DateTimeFormatter.ofPattern("yyyy-MM-dd") ));
    // 2024-06-07 10:30 로 표기
    this.replyText = reply.getReplyText();

  }


}
