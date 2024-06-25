package com.example.unknowndog.dto;

import com.example.unknowndog.constant.BoardCategory;
import com.example.unknowndog.constant.QuestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearchDTO {

    private String searchDateType;  //등록 일시
                                //상품의 등록일 기준으로

    //판매중 / 판매중지
    private BoardCategory boardCategory;

    //작성자
    private String searchBy;

    //keyword
    private String searchQuery = "";


}
