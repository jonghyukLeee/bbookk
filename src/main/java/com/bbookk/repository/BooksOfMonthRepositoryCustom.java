package com.bbookk.repository;

import com.bbookk.entity.BooksOfMonth;

import java.util.List;

public interface BooksOfMonthRepositoryCustom {
    BooksOfMonth findByBookName(String bookName);
    List<BooksOfMonth> getBooksOfMonth();
}
