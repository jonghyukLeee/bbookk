package com.bbookk.repository;

public interface MemberRepositoryCustom{
    boolean isDuplicateBook(Long id, String bookName);
}
