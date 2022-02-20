package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> ,MemberRepositoryCustom{
    Member findByLoginId(String loginId);
    Boolean existsByLoginIdAndPassword(String loginId, String password);
}
