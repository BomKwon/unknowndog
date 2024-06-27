package com.example.unknowndog.entity;

import com.example.unknowndog.dto.NoticeDTO;
import com.example.unknowndog.dto.UserDTO;
import com.example.unknowndog.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notice")
@Getter
@Setter
@ToString
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob    //@Lob은 일반적인 데이터베이스에서 저장하는 길이인 255개 이상의 문자를 저장하고 싶을 때 지정한다.
    @Column(nullable = false)
    private String content;

    private String writer;   //작성자(관리자만있음어차피)

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;   //조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void Notice(NoticeDTO noticeDTO) {
        this.title = noticeDTO.getTitle();
        this.content = noticeDTO.getContent();
        this.writer = noticeDTO.getWriter();
        this.view = noticeDTO.getView();
    }

    public void updateNotice(NoticeDTO noticeDTO) {
        this.title = noticeDTO.getTitle();
        this.content = noticeDTO.getContent();
    }
}
