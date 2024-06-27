package com.example.unknowndog.dto;

import com.example.unknowndog.constant.BoardCategory;
import com.example.unknowndog.entity.Board;
import com.example.unknowndog.entity.Notice;
import com.example.unknowndog.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long id;

    @NotBlank(message = "제목은 필수 입력값이다개")
    @Length(min = 1, max = 20,
            message = "제목은 20자 이내로 작성해주개")
    private String title;

    @NotBlank(message = "내용은 필수 입력값이다개")
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

    private List<BoardImgDTO> boardImgDTOList = new ArrayList<>();
    // 여기 이미지 넣은것처럼
    // 댓글, 리뷰등을 넣을까?
    // 상세정보보는 칸에 상단을 부트스트랩에서 텝으로 여러개 띄울수있게

    //QuestImgIds
    //이미 저장되어서 수정할때 불러온 사진들의 아이디 삭제할 이미지들
    private List<Long> boardImgIds = new ArrayList<>();


}
