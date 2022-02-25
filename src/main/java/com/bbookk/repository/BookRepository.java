package com.bbookk.repository;

import com.bbookk.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> ,BookRepositoryCustom{
}
