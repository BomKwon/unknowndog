package com.example.unknowndog.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {
    //사용자는 제품을 클릭할뿐 아이디를 입력하지는 않음
    @NotNull(message = "의뢰 아이디는 필수 입력값이개~!~")
    private Long QuestId;        // 내가 주문하는 상품id
    
    //이 값은 입력을 받음
    @Min(value = 1, message = "최소 1")
    @Max(value = 999, message = "최대 999")
    private int count;  //내가 주문한 상품 수량 최종가격은
    // itemId.price * count
    //( select price from item where item_id = :item_id )  * count

}
