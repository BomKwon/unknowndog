package com.example.unknowndog.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LikeOrderDTO {

    private Long LikeQuestId;
    // 장바구니에서 여러개의 상품을 주문하므로 cartorderDTO 클래스가 자기
    //자신을 list로 가지고 있도록
    private List<LikeOrderDTO> likeOrderDTOList;
}
