package com.example.unknowndog.repository;

import com.example.unknowndog.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

        //주문한 목록을 받아오기 위해서
        @Query("select o from Order o " +
                "where o.user.email = :email " +
                "order by o.orderDate desc")
        List<Order> findOrders (@Param("email") String email, Pageable pageable);

        //주문 수량 이메일을 기준으로 이메일은 username 시큐리티에서 id 로그인한사람

        @Query("select count(o) from Order o " +
                "where o.user.email = :email")
        Long countOder(@Param("email")String email);

//        Long countByEmail (@Param("email") String email);

}
