package com.example.unknowndog.dto;

import com.example.unknowndog.entity.QuestImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
public class QuestImgDTO {

    private int id;


    private String imgName;     //이미지 파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;   //이미지 조회 경로

    private String repimgYn;        // 대표이미지 여부

    // 메소드  EntityToDto // DtoToEntity
    private static ModelMapper modelMapper = new ModelMapper();



    public static QuestImgDTO of(QuestImg questImg){

        return modelMapper.map(questImg, QuestImgDTO.class);
    }




}
