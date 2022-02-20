package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.repository.dto.LibraryDto;
import com.bbookk.repository.dto.NoneBooksDto;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom{
    boolean isDuplicateBook(Long id, String bookName);
    List<LibraryDto> getLibrary(Long id);
    Book findMemberBook(Long id, String bookName);
    List<NoneBooksDto> findBooksByGu(String gu);
}
