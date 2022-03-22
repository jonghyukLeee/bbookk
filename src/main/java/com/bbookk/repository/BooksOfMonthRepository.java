package com.bbookk.repository;

import com.bbookk.entity.BooksOfMonth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksOfMonthRepository extends JpaRepository<BooksOfMonth, Long> ,BooksOfMonthRepositoryCustom{

}
