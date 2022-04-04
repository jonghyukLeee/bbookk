package com.bbookk.repository;

import com.bbookk.entity.Member;
import com.bbookk.repository.dto.AddressDto;
import com.bbookk.repository.dto.ProfileDto;


public interface MemberRepositoryCustom{
    Member findByLoginId(String loginId);
    boolean isDuplicateBook(Long id, String bookName);
    ProfileDto getProfile(Long memberId);
    AddressDto getAddress(Long id);
}
