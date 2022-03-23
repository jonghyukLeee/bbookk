package com.bbookk.entity;

import com.bbookk.repository.BookRepository;
import com.bbookk.repository.OrderRepository;
import com.bbookk.repository.dto.RequestsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional(readOnly = true)
public class OrderTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BookRepository bookRepository;

    
    @Test
    @Transactional
    void setOrderTest()
    {
        Book getBook = bookRepository.findMemberBook(1L, "달러구트 꿈 백화점");
        Orders order = new Orders(2L,"박철준",getBook);
        orderRepository.save(order);
        getBook.setStatus(OrderStatus.REQUESTED);

        em.persist(getBook);
        em.flush();

        List<RequestsDto> requestedOrders = orderRepository.getRequestedOrders(1L);

        for (RequestsDto requestedOrder : requestedOrders) {
            System.out.println(requestedOrder.getBookName());
        }

    }

    @Test
    void getStatus()
    {
        Book getBook = bookRepository.findMemberBook(1L, "달러구트 꿈 백화점");
        System.out.println(getBook.getStatus());
        getBook.setStatus(OrderStatus.REQUESTED);
        System.out.println(getBook.getStatus());
    }
}
