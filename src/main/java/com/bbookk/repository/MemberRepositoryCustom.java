package com.bbookk.repository;

import com.bbookk.entity.Member;

import java.util.Optional;

public interface MemberRepositoryCustom{
    Member findByLoginId(String loginId);
    boolean isDuplicateBook(Long id, String bookName);
}
