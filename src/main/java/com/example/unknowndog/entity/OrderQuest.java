package com.example.unknowndog.entity;

import com.example.unknowndog.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity //jpa에서 관리를 할수 있습니다. 엔티티매니저
@Table(name = "order_quest")   //jpa를 이용할 때 자동으로 데이터베이스 설정과 데이터베이스 내 테이블을 같이 확인하기때문에 에러 나올수 있음
// 데이터베이스상 어떤 테이블로 생성할 것인 정보를 담기 위한 어노테이션
@Getter
@Setter
@ToString
public class OrderQuest extends BaseEntity {


    @Id
    @Column(name = "order_quest_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    private Quest quest;              //아이템

    private String salaryOption;

    private int orderPrice;

    private String area;

    private int count;  //수량  제품당 담는 수량


    public static OrderQuest createOrderQuest(Quest quest, int count){
        //item은 검색한 아이템 //removeStock()를 통해서 수량 변경
        OrderQuest orderQuest = new OrderQuest();

        orderQuest.setQuest(quest);        //산 아이템
        orderQuest.setCount(count);      //구매수량

        orderQuest.setSalaryOption(quest.getSalaryOption());

        orderQuest.setOrderPrice(quest.getSalary());

        orderQuest.setArea(quest.getArea());

        quest.removeStock(count);

        return orderQuest;
    }
    public int getTotalPrice(){     //주문한 금액
        return orderPrice * count;
    }


    public void cancel(){
        this.getQuest().stockUpdate(count);
    }





}
