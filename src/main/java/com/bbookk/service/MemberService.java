package com.bbookk.service;

import com.bbookk.controller.form.ModifyForm;
import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.repository.BookRepository;
import com.bbookk.repository.MemberRepository;
import com.bbookk.repository.dto.LibraryDto;
import com.bbookk.repository.dto.NoneBooksDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        //memberRepository.save(findMember);
    }
    @Transactional
    public void drop(Member member)
    {
        memberRepository.delete(member);
    }

    @Transactional
    public void addBook(Member member, Book book)
    {
        Optional<Member> findMember = memberRepository.findById(member.getId());

        findMember.ifPresent(book::setMember);
        bookRepository.save(book);
    }

    public boolean isDuplicateId(String loginId)
    {
        return memberRepository.findByLoginId(loginId) != null;
    }

    public boolean isDuplicatedBook(Long id,String bookName)
    {
        return memberRepository.isDuplicateBook(id,bookName);
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

    public List<LibraryDto> getLibrary(Member member)
    {
        return memberRepository.getLibrary(member.getId());
    }

    @Transactional
    public void deleteBook(Long id, String bookName) {
        bookRepository.delete(memberRepository.findMemberBook(id,bookName));
    }

    public List<NoneBooksDto> getBooksByGu(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        return memberRepository.findBooksByGu(findMember.get().getAddress().getGu());
    }
}
