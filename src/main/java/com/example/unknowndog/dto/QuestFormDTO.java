package com.example.unknowndog.dto;

import com.example.unknowndog.constant.QuestStatus;
import com.example.unknowndog.entity.Quest;
import com.example.unknowndog.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestFormDTO {


    private Long id; //글 번호

    private String writer; //작성자(회원별명)

    @NotBlank(message = "제목은 필수 입력 값이다개")
    @Length(min = 1, max = 25,
            message = "제목은 25자 이내로 작성해주개")
    private String title; //글 제목

    @NotBlank(message = "급여옵션은 필수 입력 값이다개")
    private String salaryOption; //시급, 일급, 무료 등등

    @PositiveOrZero(message = "급여 입력은 0 이상의 값이다개")
    private int salary;  //급여

    private String area; //지역(이건 회원 주소랑 연동)

    private int stockNumber; //재고수량 , 1 고정

    @NotBlank(message = "상세 설명은 필수 입력 값이다개")
    private String questDetail;  //글 상세 내용

    private QuestStatus questStatus; // 상품 판매 상태

    // 메소드  EntityToDto // DtoToEntity
    private static ModelMapper modelMapper = new ModelMapper();

    private User userId;

    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    private String createBy;
    private String modifiedBy;

    private int view;


    public Quest newQuest(){

        return modelMapper.map(this, Quest.class);
    }

    public static QuestFormDTO of(Quest quest){

        return modelMapper.map(quest, QuestFormDTO.class);
    }


    private List<QuestImgDTO> questImgDTOList = new ArrayList<>();
    // 여기 이미지 넣은것처럼
    // 댓글, 리뷰등을 넣을까?
    // 상세정보보는 칸에 상단을 부트스트랩에서 텝으로 여러개 띄울수있게
    
    //QuestImgIds
    //이미 저장되어서 수정할때 불러온 사진들의 아이디 삭제할 이미지들
    private List<Long> questImgIds = new ArrayList<>();


}
