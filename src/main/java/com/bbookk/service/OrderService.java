package com.bbookk.service;

import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.entity.OrderStatus;
import com.bbookk.entity.Orders;
import com.bbookk.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Transactional
    public void acceptRequest(Long orderId)
    {
        Optional<Orders> findOrder = orderRepository.findById(orderId);
        findOrder.get().setStatus(OrderStatus.LEND);
    }

    @Transactional
    public void setMember(Long orderId,Long memberId) {
        Optional<Orders> findOrder = orderRepository.findById(orderId);
        Optional<Member> findMember = memberRepository.findById(memberId);
        findOrder.get().setMember(findMember.get());
    }

    @Transactional
    public void returnRequest(Long memberId, String bookName) {
        Orders findOrder = orderRepository.findByBorrowerIdAndBook(memberId, bookName);
        findOrder.setStatus(OrderStatus.RETURN);
    }

    @Transactional
    public void delete(Long orderId) {
        Optional<Orders> findOrder = orderRepository.findById(orderId);
        Book findBook = orderRepository.findBookById(orderId);
        findBook.setStatus(null);
        orderRepository.delete(findOrder.get());
    }
}
