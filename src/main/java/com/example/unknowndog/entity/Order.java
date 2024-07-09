package com.example.unknowndog.entity;

import com.example.unknowndog.constant.OrderStatus;
import com.example.unknowndog.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity //jpa에서 관리를 할수 있습니다. 엔티티매니저
@Table(name = "orders")   //jpa를 이용할 때 자동으로 데이터베이스 설정과 데이터베이스 내 테이블을 같이 확인하기때문에 에러 나올수 있음
// 데이터베이스상 어떤 테이블로 생성할 것인 정보를 담기 위한 어노테이션
@Getter
@Setter
public class Order  extends BaseEntity {


    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL, orphanRemoval = true
                , fetch = FetchType.LAZY)
    private List<OrderQuest> orderQuests = new ArrayList<>();

    private LocalDateTime orderDate;

    //주문 취소, 주문
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //주문아이템들을 만든다. 위에 List 주문아이템들 있다.

    public void addOrderItem(OrderQuest orderQuest){//아이템 하나를 받음
        orderQuests.add(orderQuest);      //위에 아이템리스트에 넣는다.
        //주문은 주문아이템들을  가지고 있다.
        orderQuest.setOrder(this);   //주문아이템을 주문을 참조한다.
    }

    public static Order createOrder(User user, List<OrderQuest> orderQuestList){
        Order order = new Order();  //저장에 쓰일 주문엔티티 만드는 메소드
        order.setUser(user);

        for (OrderQuest orderQuest : orderQuestList){
            order.addOrderItem(orderQuest);      //주문에 있는 주문아이템리스트 넣기 반복해서
        }

        order.setOrderStatus(OrderStatus.ORDER);    //주문상태
        order.setOrderDate(LocalDateTime.now());    //주문시간

        //그렇게 만들어진 엔티티를 반환한다.
        return order;
    }

//    public int getTotalPrice(){
//        //하나의 주문에 있는 전체 아이템들의 가격 총합
//        int totalPrice = 0;
//
//        for(OrderQuest orderQuest : orderQuests){
//            //주문한 하나의 아이템의 가격 * 수량
//            totalPrice += orderQuest.getTotalPrice();
//        }
//        return totalPrice;
//    }


    public void cacelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for (OrderQuest orderQuest : orderQuests){
            orderQuest.cancel();
        }
    }





}
