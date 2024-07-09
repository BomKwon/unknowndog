package com.example.unknowndog.entity;

import com.example.unknowndog.constant.BoardCategory;
import com.example.unknowndog.constant.QuestStatus;
import com.example.unknowndog.dto.BoardDTO;
import com.example.unknowndog.dto.NoticeDTO;
import com.example.unknowndog.dto.QuestFormDTO;
import com.example.unknowndog.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
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

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory; // 상품 판매 상태

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardImg> boardImgList = new ArrayList<>();


    public void updateBoard(BoardDTO boardDTO) {
        this.title = boardDTO.getTitle();
        this.content = boardDTO.getContent();
        this.boardCategory = boardDTO.getBoardCategory();
    }

    public Board(Long id, String title, String content, String writer, int view, User user, BoardCategory boardCategory, List<BoardImg> boardImgList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.view = view;
        this.user = user;
        this.boardCategory = boardCategory;
        this.boardImgList = boardImgList;
    }

}
