package com.example.unknowndog.dto;


import com.example.unknowndog.constant.OrderStatus;
import com.example.unknowndog.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderHistDTO {

    private Long orderId; //주문아이디
    private String OrderDate; // 주문날짜
    private OrderStatus orderStatus; //주문상태

    //주문상품 리스트
    private List<OrderQuestDTO> orderQuestDTOList = new ArrayList<>();

    //생성자
    public OrderHistDTO(Order order){

        this.orderId = order.getId();
        //this.OrderDate = order.getOrderDate();  //String타입으로 변환을 위해서
                                                // format 적용
        this.OrderDate = order.getOrderDate()
                .format(  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm") );
                                                // 2024-06-07 10:30 로 표기
        this.orderStatus = order.getOrderStatus();

    }

    //메소드
    public  void addOrderQuestDTO (OrderQuestDTO orderQuestDTO){
        orderQuestDTOList.add(orderQuestDTO);
    }


}
