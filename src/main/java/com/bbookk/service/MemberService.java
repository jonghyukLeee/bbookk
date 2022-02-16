package com.bbookk.service;

import com.bbookk.controller.form.ModifyForm;
import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.repository.BookRepository;
import com.bbookk.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void join(Member member)
    {
        memberRepository.save(member);
    }

    @Transactional
    public void modify(Member member, ModifyForm form)
    {
        Member findMember = memberRepository.findByLoginId(member.getLoginId());
        findMember.modify(form);
        findMember.setPassword(form.getPassword());
        memberRepository.save(findMember);
    }

    @Transactional
    public void addBook(Member member, Book book)
    {
        Optional<Member> findMember = memberRepository.findById(member.getId());
        book.setMember(findMember.get());
        bookRepository.save(book);
    }

    public boolean isDuplicateId(String loginId)
    {
        return memberRepository.findByLoginId(loginId) != null;
    }

    public boolean isMember(String loginId, String password) {
        return memberRepository.existsByLoginIdAndPassword(loginId,password);
    }

    @Transactional
    public void addCash(Member member,int amount)
    {
        member.addCash(amount);
        memberRepository.save(member);
    }
}
