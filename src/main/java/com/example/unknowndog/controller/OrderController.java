package com.example.unknowndog.controller;

import com.example.unknowndog.dto.OrderDTO;
import com.example.unknowndog.dto.OrderHistDTO;
import com.example.unknowndog.service.MainService;
import com.example.unknowndog.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
public class OrderController {

    private final MainService mainService;
    private final OrderService orderService;

    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDTO orderDTO,
                                        BindingResult bindingResult, Principal principal, Model model){

        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);

        log.info("주문한 상품 정보 :" + orderDTO);
        //principal : 현재 로그인된 사람의 정보
        log.info("로그인 정보" + principal);
        //여기서 이름은 실제 이름이 아닌 시큐리티의 username 즉 id를 뜻함
        //로그인을 하지 않았다면 에러 차후에 예외처리와 로그인처리 해야함
        if(principal == null){
            return new ResponseEntity<String>("로그인로그인" , HttpStatus.UNAUTHORIZED);
        }

        log.info("로그인한 사람 이름 " + principal.getName());
        log.info(orderDTO);
        //유효성검사
        if (bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                stringBuilder.append(fieldError.getDefaultMessage());
            }
            log.info(stringBuilder);

            return new ResponseEntity<String>(stringBuilder.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long orderId;

        try {
            // 서비스에서 주문을 내고 db에 저장된 값을 돌려 받는다. 그중에 id
            // orderId = 리턴
            orderId =  orderService.order(orderDTO, email);
            log.info("주문한 orderId : " + orderId);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // 입력받은 파라미터가 정상적이라면
        // 이후 service를 통해서 저장, 수정, 삭제
        // 읽기 등을 동일하게 수행하면 됨

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }



    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable Optional<Integer> page,
                            Principal principal, Model model) {


        String nickname = mainService.getUserName(principal);
        model.addAttribute("nickname", nickname);


        Pageable pageable = PageRequest
                .of(page.isPresent() ? page.get() : 0 , 20);


//        principal.getName()은 로그인한 아이디
        Page<OrderHistDTO> orderHistDTOPage
                = orderService.getOrderList(principal.getName(),pageable);

        List<OrderHistDTO>a = orderHistDTOPage.getContent();

        model.addAttribute("orderHistDTOPage", orderHistDTOPage);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "/order/orderHist";
    }



    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(
            @PathVariable("orderId") Long orderId,
            Principal principal
    ){      //orderId 이걸로 select memberuser_id from order where orderId = :orderId
        //  principat.getName() ==  select email from memberuser where memberuser_id = : memberuser_id


        String email = principal.getName(); //로그인한 사용자 이메일

        if( !orderService.validateOrder(orderId, email) ){

            return new ResponseEntity<String>("주문 취소 권한이 없습니다."
                    , HttpStatus.FORBIDDEN);
        }

        orderService.cancelOrder(orderId);  //주문번호를 이용해서
        //주문상태를 cancel부터
        //아이템에 addStockNumber실행

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);

    }







}
