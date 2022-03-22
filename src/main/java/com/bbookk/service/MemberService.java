package com.bbookk.service;

import com.bbookk.controller.form.ModifyForm;
import com.bbookk.entity.Book;
import com.bbookk.entity.BooksOfMonth;
import com.bbookk.entity.Member;
import com.bbookk.entity.Orders;
import com.bbookk.repository.BookRepository;
import com.bbookk.repository.BooksOfMonthRepository;
import com.bbookk.repository.MemberRepository;
import com.bbookk.repository.OrderRepository;
import com.bbookk.repository.dto.LibraryDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Order;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final BooksOfMonthRepository bomRepository;

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
        //처음 등록된 책이면 Bom에 insert
        BooksOfMonth getBom = bomRepository.findByBookName(book.getBookName());
        if(getBom == null)
        {
            BooksOfMonth bom = new BooksOfMonth(book.getImgSource(),book.getBookName());
            bomRepository.save(bom);
        }
        else getBom.addCnt();
        bookRepository.save(book);
    }

    @Transactional
    public void addCashResisterBook(Long id)
    {
        Optional<Member> findMember = memberRepository.findById(id);
        findMember.get().addCash(500);
    }
    @Transactional
    public void addCashLendBook(Long id)
    {
        Member findMember = memberRepository.findById(id).get();
        findMember.addCash(200);
    }

    @Transactional
    public boolean subCashBorrowRequest(Long id)
    {
        Member findMember = memberRepository.findById(id).get();
        if(findMember.getCash() < 200) return false;
        findMember.subCash(200);
        return true;
    }

    public Page<LibraryDto> getLibrary(Member member, Pageable pageable)
    {
        return bookRepository.getLibrary(member.getId(),pageable);
    }

    @Transactional
    public boolean deleteBook(Long id, String bookName) {
        Book findBook = bookRepository.findMemberBook(id, bookName);
        if(findBook != null)
        {
            bookRepository.delete(findBook);
            return true;
        }
        return false;
    }

    @Transactional
    public void createOrder(Long borrowerId, Book findBook) {
        Orders order = new Orders(borrowerId,memberRepository.findById(borrowerId).get().getName(),findBook);
        orderRepository.save(order);
        findBook.setRequested();
    }
}
