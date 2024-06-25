package com.example.unknowndog.dto;

import java.time.LocalDateTime;

public class MainBoardDTO {

    private Long id; //글 번호

    private String nickname; //작성자(회원별명)

    private String title; //글 제목

    private LocalDateTime regidate; //작성일

    private int view;  //조회수

}
