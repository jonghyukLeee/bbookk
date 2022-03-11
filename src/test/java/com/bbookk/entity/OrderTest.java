package com.bbookk.entity;

import com.bbookk.repository.BookRepository;
import com.bbookk.repository.OrderRepository;
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
        Orders order = new Orders(2L);
        orderRepository.save(order);

        Book getBook = bookRepository.findMemberBook(1L, "달러구트 꿈 백화점");
        getBook.setOrder(order);

        em.persist(getBook);
        em.flush();

        List<Orders> res = em.createQuery("select o from Orders o", Orders.class).getResultList();

        for (Orders re : res) {
            System.out.println(re.getBorrower_id());
        }

    }
}
