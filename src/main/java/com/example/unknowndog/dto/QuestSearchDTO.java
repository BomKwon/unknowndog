package com.example.unknowndog.dto;

import com.example.unknowndog.constant.QuestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestSearchDTO {

    private String searchDateType;  //등록 일시
                                //상품의 등록일 기준으로

    //판매중 / 판매중지
    private QuestStatus questStatus;

    //지역별로
    private String area;

    //판매자로
    private String searchBy;

    //keyword
    private String searchQuery = "";


}
