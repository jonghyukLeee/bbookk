package com.bbookk.service;

import com.bbookk.entity.Orders;
import com.bbookk.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void acceptRequest(Long orderId)
    {
        Optional<Orders> findOrder = orderRepository.findById(orderId);
        findOrder.get().setLend();
    }
}
