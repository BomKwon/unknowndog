package com.example.unknowndog.dto;


import com.example.unknowndog.entity.OrderQuest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderQuestDTO {
    //주문이력 / 구매이력 페이지에 필요한dto
    // 매출내역? 주문내역

    //생성자로 dto생성 기본생성자 X

    private String title;  //의뢰명

    private String salaryOption;

    private int orderPrice; //주문금액

    private int count;      //수량

    private String area;

    private String imgUrl; //상품이미지
    // 상품 상세페이지(주문이가능한 페이지)비슷함

    // 주문들의 정보를 담을 OrderHistDTO가 필요하다
    // 한번에 여러가지의 아이템을 주문시 주문아이템이 여러개라서
    //묶어주는 주문 1번의 DTO가 필요

    public OrderQuestDTO(OrderQuest orderQuest, String imgUrl) {
        // 주문아이템을 받아서
        this.title = orderQuest.getQuest().getTitle();
        this.count = orderQuest.getCount();
        this.salaryOption = orderQuest.getSalaryOption();
        this.orderPrice = orderQuest.getOrderPrice();
        this.area = orderQuest.getArea();
        this.imgUrl = imgUrl;
    }

//    public OrderItemDTO(){
//
//    }

}
