package com.example.unknowndog.dto;

import com.example.unknowndog.entity.BoardImg;
import com.example.unknowndog.entity.QuestImg;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardImgDTO {

    private int id;

    private String imgName;     //이미지 파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;   //이미지 조회 경로

    private String repimgYn;        // 대표이미지 여부

    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    private String createBy;
    private String modifiedBy;

    // 메소드  EntityToDto // DtoToEntity
    private static ModelMapper modelMapper = new ModelMapper();




    public static ModelMapper getMapper(){

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;

    }

    public static BoardImgDTO of(BoardImg boardImg) {
        return getMapper().map(boardImg, BoardImgDTO.class);
    }



}
