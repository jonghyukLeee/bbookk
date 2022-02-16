package com.bbookk.repository;

import com.bbookk.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> ,MemberRepositoryCustom{
    Member findByLoginId(String loginId);
    Boolean existsByLoginIdAndPassword(String loginId, String password);

//    @Modifying(clearAutomatically = true)
//    @Query("update Member m set m.cash = m.cash + :amount where m.id = :id")
//    void addCash(@Param("id") Long id, @Param("amount")int amount);
}
