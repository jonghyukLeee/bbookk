package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.repository.dto.LibraryDto;
import com.bbookk.repository.dto.FindBooksDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom{
    boolean isDuplicateBook(Long id, String bookName);
    List<LibraryDto> getLibrary(Long id);
    Book findMemberBook(Long id, String bookName);
    Page<FindBooksDto> findBooks(String gu, String query, Pageable pageable);
}
