package com.example.unknowndog.dto;

import com.example.unknowndog.constant.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDTO {

    private String searchDateType;  //가입 일시


    private String searchBy;

    //keyword
    private String searchQuery = "";

    private Role role;

}
