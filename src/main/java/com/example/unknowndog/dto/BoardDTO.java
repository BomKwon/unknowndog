package com.example.unknowndog.dto;

import com.example.unknowndog.constant.BoardCategory;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.User;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

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

    private BoardCategory boardCategory; // 상품 판매 상태




    // 메소드  EntityToDto // DtoToEntity
    private static ModelMapper modelMapper = new ModelMapper();


    public Board newBoard(){

        return modelMapper.map(this, Board.class);
    }

    public static BoardDTO of(Board board){

        return modelMapper.map(board, BoardDTO.class);
    }
}
