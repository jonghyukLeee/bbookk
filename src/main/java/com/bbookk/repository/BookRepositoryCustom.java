package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.repository.dto.BookDetailsDto;
import com.bbookk.repository.dto.FindBooksDto;
import com.bbookk.repository.dto.LibraryDto;
import com.bbookk.repository.dto.MyBookDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryCustom {
    List<LibraryDto> getLibrary(Long id);
    Book findMemberBook(Long id, String bookName);
    Page<FindBooksDto> findBooks(String gu, String query, Pageable pageable);
    //BookDetailsDto getBookDetails(Long id, String bookName);
}
