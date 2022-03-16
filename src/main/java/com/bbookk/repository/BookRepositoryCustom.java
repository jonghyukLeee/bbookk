package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.repository.dto.BorrowBooksDto;
import com.bbookk.repository.dto.LibraryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<LibraryDto> getLibrary(Long id,Pageable pageable);
    Book findMemberBook(Long id, String bookName);
    Page<BorrowBooksDto> findBooks(Long id, String gu, String query, Pageable pageable);
}
