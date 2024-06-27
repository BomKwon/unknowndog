package com.example.unknowndog.dto;

import com.example.unknowndog.constant.BoardCategory;
import com.example.unknowndog.constant.QuestStatus;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class MainBoardDTO {

    private Long id; //글 번호

    private String writer; //작성자(회원별명)

    private String title; //글 제목

    private LocalDateTime regidate; //작성일

    private int view;  //조회수

    private BoardCategory boardCategory;


    @QueryProjection
    public MainBoardDTO(Long id, String writer, String title, int view, LocalDateTime regidate, BoardCategory boardCategory) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.view = view;
        this.regidate = regidate;
        this.boardCategory = boardCategory;
    }

}
