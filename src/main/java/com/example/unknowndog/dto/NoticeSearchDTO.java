package com.example.unknowndog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeSearchDTO {

    private String searchDateType;  //등록 일시

    //판매자로
    private String searchBy;

    //keyword
    private String searchQuery = "";


}
