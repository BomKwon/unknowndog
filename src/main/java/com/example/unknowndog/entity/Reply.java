package com.example.unknowndog.entity;

import com.example.unknowndog.dto.ReplyDTO;
import com.example.unknowndog.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reply")
//@ToString
public class Reply extends BaseEntity {

  @Id
  @Column(name = "reply_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "board_id")
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Board board; //참조대상

  private String replyText; // 내용

  private String replyer; //작성자

  public void changeText(String text){
    this.replyText = text;
  }


  public void Reply(ReplyDTO replyDTO) {
    this.id = replyDTO.getId();
    this.replyText = replyDTO.getReplyText();
    this.replyer = replyDTO.getReplyer();
  }


}
