package com.bbookk.service;

import com.bbookk.controller.form.ModifyForm;
import com.bbookk.entity.Book;
import com.bbookk.entity.Member;
import com.bbookk.entity.Orders;
import com.bbookk.repository.BookRepository;
import com.bbookk.repository.MemberRepository;
import com.bbookk.repository.OrderRepository;
import com.bbookk.repository.dto.LibraryDto;
import com.bbookk.repository.dto.FindBooksDto;
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
    private final OrderRepository orderRepository;

    @Transactional
    public void join(Member member)
    {
        memberRepository.save(member);
    }

    @Transactional
    public void modify(Member member, ModifyForm form)
    {
        Member findMember = memberRepository.findById(member.getId()).get();
        findMember.modify(form);
        findMember.setPassword(form.getPassword());
    }

    //중복확인
    public boolean isDuplicatedLoginId(String loginId)
    {
        return memberRepository.findByLoginId(loginId) != null;
    }

    public boolean isDuplicatedNickname(String nickname) {
        return memberRepository.findByNickname(nickname) != null;
    }

    public boolean isDuplicatedBook(Long id,String bookName)
    {
        return memberRepository.isDuplicateBook(id,bookName);
    }

    @Transactional
    public void drop(Member member)
    {
        memberRepository.delete(member);
    }

    @Transactional
    public void addBook(Long id, Book book)
    {
        Optional<Member> findMember = memberRepository.findById(id);

        findMember.ifPresent(book::setMember);
        bookRepository.save(book);
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
        return bookRepository.getLibrary(member.getId());
    }

    @Transactional
    public void deleteBook(Long id, String bookName) {
        bookRepository.delete(bookRepository.findMemberBook(id,bookName));
    }

    @Transactional
    public String createOrder(Book findBook, Long borrowerId) {
        if(findBook.getOrder() == null)
        {
            Orders order = new Orders(borrowerId);
            orderRepository.save(order);
            findBook.setOrder(order);
            return "success";
        }
        return "fail";
    }


}
