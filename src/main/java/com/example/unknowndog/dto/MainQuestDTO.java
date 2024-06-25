package com.example.unknowndog.dto;

import com.example.unknowndog.constant.QuestStatus;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MainQuestDTO {


    private Long id; //글 번호

    private String nickname; //작성자(회원별명)

    private String title; //글 제목

    private String salaryOption; //시급, 일급, 무료 등등

    private int salary;  //급여

    private String imgUrl;  // 이미지

    private String area; //지역(이건 회원 주소랑 연동)

    private String questDetail;  //글 상세 내용

    private QuestStatus questStatus;  //의뢰 상태

    private LocalDateTime regTime;



    //생성자에 @QueryProjection 어노테이션을 선언하여 Querydsl로 결과 조회시
    // MainitemDTO 객체로 바로 받아오도록 활용
    // select *<<entity x >> dto로 바뀐다. from table

    @QueryProjection
    public MainQuestDTO(Long id, String nickname, String title, String salaryOption, int salary, String imgUrl, String area, String questDetail, QuestStatus questStatus) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.salaryOption = salaryOption;
        this.salary = salary;
        this.imgUrl = imgUrl;
        this.area = area;
        this.questDetail = questDetail;
        this.questStatus = questStatus;
    }
}
