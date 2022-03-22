package com.bbookk.repository;

import com.bbookk.entity.Member;
import com.bbookk.repository.dto.ProfileDto;

import java.util.Optional;

public interface MemberRepositoryCustom{
    Member findByLoginId(String loginId);
    boolean isDuplicateBook(Long id, String bookName);
    ProfileDto getProfile(Long memberId);
}
