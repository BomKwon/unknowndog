package com.example.unknowndog.dto;

import com.example.unknowndog.constant.QuestStatus;
import com.example.unknowndog.entity.Quest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikeDetailDTO {

    private Long LikeQuestId;    //장바구니 상품 아이디
                                //게시판에서 bno처럼 아이템의 아이디를 가지고
                                //차후에 주문을 하던가 삭제를 하는데 사용

    private Long questId;

    private String title;     // 상품명

    private int salary;          //상품 금액

    private int count;          // 수량

    private String imgUrl;      //상품 이미지 경로

    private String area;

    private QuestStatus questStatus;

    private String salaryOption;

    public LikeDetailDTO(Long LikeQuestId, Long questId, String title, int salary, int count,
                         String imgUrl, String area, QuestStatus questStatus, String salaryOption) {
        this.LikeQuestId = LikeQuestId;
        this.questId = questId;
        this.title = title;
        this.salary = salary;
        this.count = count;
        this.imgUrl = imgUrl;
        this.area = area;
        this.questStatus = questStatus;
        this.salaryOption = salaryOption;
    }

}
