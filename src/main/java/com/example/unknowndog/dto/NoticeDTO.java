package com.example.unknowndog.dto;

import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.Quest;
import com.example.unknowndog.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {

    private Long id;

    private String title;

    private String content;

    private String writer;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    private String createBy;
    private String modifiedBy;

    private int view;

    private User user;



    // 메소드  EntityToDto // DtoToEntity
    private static ModelMapper modelMapper = new ModelMapper();


    public Notice newNotice(){

        return modelMapper.map(this, Notice.class);
    }

    public static NoticeDTO of(Notice notice){

        return modelMapper.map(notice, NoticeDTO.class);
    }
}
