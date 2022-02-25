package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.repository.dto.BookDetailsDto;
import com.bbookk.repository.dto.FindBooksDto;
import com.bbookk.repository.dto.LibraryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepositoryCustom {
    List<LibraryDto> getLibrary(Long id);
    Book findMemberBook(Long id, String bookName);
    Page<FindBooksDto> findBooks(String gu, String query, Pageable pageable);
    BookDetailsDto getBookDetails(Long id, String bookName);
}
