package com.example.unknowndog.service;


import com.example.unknowndog.dto.OrderDTO;
import com.example.unknowndog.dto.OrderHistDTO;
import com.example.unknowndog.dto.OrderQuestDTO;
import com.example.unknowndog.entity.*;
import com.example.unknowndog.repository.OrderRepository;
import com.example.unknowndog.repository.QuestImgRepository;
import com.example.unknowndog.repository.QuestRepository;
import com.example.unknowndog.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final QuestRepository questRepository;
    private final UserRepository userRepository;
    private final QuestImgRepository questImgRepository;

    // 주문
    public Long order(OrderDTO orderDTO , String email){
        //아이템을 찾는다.
        Quest quest = questRepository
                .findById(orderDTO.getQuestId())
                .orElseThrow(EntityNotFoundException::new);
        // 로그인한사람을 찾는다.

        User user =
                userRepository.findByEmail(email);

        List<OrderQuest> orderquestList = new ArrayList<>();

        OrderQuest orderquest = OrderQuest.createOrderQuest(quest, orderDTO.getCount());
        //하나의 주문DTO를 주문아이템리스트 넣기
        orderquestList.add(orderquest);

        //만들어진 주문아이템리스트를  아이디와 함께 order 엔티티만들기
        Order order = Order.createOrder(user, orderquestList);
        // order entity를 만들어서 저장을 수행

            orderRepository.save(order);    //반환타입은 order인데


        log.info("order테이블에 저장" + order.getId());
        //리턴값은 저장된 orderID를 반환
        return  order.getId();

    }
    
    //주문목록
    @Transactional(readOnly = true) //읽기만 entity수정 안되도록
    public Page<OrderHistDTO> getOrderList(String email, Pageable pageable) {
        // 로그인된 사용자의 email과 페이징처리를 위해
        //controller로부터 pageable을 파라미터로 입력받는다.

        // 사용자가 주문한 목록
        List<Order> orderList = orderRepository.findOrders(email, pageable);
        // 입력받은 이메일과 pageable로 주문 목록을 받아온다.
        // 페이징처리를 위한 전체 목록수를 받아온다.
        Long totalCount = orderRepository.countOder(email);

        //주문아이템들을 리스트로 만들기위해 list생성
        List<OrderHistDTO> orderHistDTOList = new ArrayList<>();

        //받아온 Order객체 안에는
        for (Order order  :orderList){
            //order를 DTO로 변경
            OrderHistDTO orderHistDTO = new OrderHistDTO(order);
            //quest들이 있고 그 아이템들을
            List<OrderQuest> orderquestList = order.getOrderQuests();

            for (OrderQuest orderquest  :orderquestList){
                // order에는 아이템의 이미지가 없기에
                // 아이템의 아이디를 통해, 대표이미지인거 찾기
                QuestImg questImg
                        = questImgRepository
                        .findByQuestIdAndRepimgYn(orderquest.getQuest().getId(), "Y");
                //아이템과, 아이템의 이미지를 통해서 OrderquestDTO를 만들고
                OrderQuestDTO orderquestDTO
                        = new OrderQuestDTO(orderquest, questImg.getImgUrl());
                //만들어진 orderquestDTO로  orderHistDTO를 만들고
                orderHistDTO.addOrderQuestDTO(orderquestDTO);
            }
            //만들어진 orderHistDTO로 orderHistDTOList를 만들고
            orderHistDTOList.add(orderHistDTO);
        }
        return new PageImpl<OrderHistDTO>(orderHistDTOList, pageable, totalCount);
    }



    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email) {
        //로그인한 사람의 email로 찾은 유저
        User cur = userRepository.findByEmail(email);
        //주문이 참조하고있는 유저와
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        User saved = order.getUser();
        //로그인한사람과 주문한사람이 같지 않다면  false
        if(!StringUtils.equals(cur.getEmail(), saved.getEmail())){
            return false;
        }
        //같다면 true
        return true;

    }
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        order.cacelOrder();

        //public void cacelOrder(){
        //        this.orderStatus = OrderStatus.CANCEL; 취소상태
        //
        //        for (Orderquest orderquest  : orderquests){
        //            orderquest.cancel();       // quest의 수량을 다시 더해줍니다.
        //        }
        //    }
        //    public void cancel(){
        //        this.getquest().addStock(count);
        //    }

    }

    public  Long    orders(List<OrderDTO> orderDTOList, String email){

        User user = userRepository.findByEmail(email);

        List<OrderQuest> orderquestList = new ArrayList<>();

        for(OrderDTO orderDTO : orderDTOList){
            Quest quest = questRepository
                    .findById(orderDTO.getQuestId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderQuest orderquest = OrderQuest.createOrderQuest(quest, orderDTO.getCount());

            orderquestList.add(orderquest);

        }

        Order order = Order.createOrder(user,orderquestList);  //dtoToEntity

        orderRepository.save(order);

        return order.getId();

    }

    public void removeOrders(Long orderId) {

        orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        orderRepository.deleteById(orderId);

    }









}
