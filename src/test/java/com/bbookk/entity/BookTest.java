package com.bbookk.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
public class BookTest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void getStatus()
    {
        Book book = new Book("img","name","au","pub","isbn");
        book.setRequested();
        em.persist(book);
        em.flush();

        List<Book> res = em.createQuery("select b from Book b", Book.class).getResultList();
        for (Book re : res) {
            System.out.println(re.getStatus());
        }
    }
}
