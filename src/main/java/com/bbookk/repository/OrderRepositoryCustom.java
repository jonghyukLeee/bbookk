package com.bbookk.repository;

import com.bbookk.entity.Book;
import com.bbookk.entity.Orders;
import com.bbookk.repository.dto.BorrowListDetailsDto;
import com.bbookk.repository.dto.BorrowListDto;
import com.bbookk.repository.dto.RequestsDto;

import java.util.List;

public interface OrderRepositoryCustom {
    List<RequestsDto> getRequestedOrders(Long id);
    List<RequestsDto> getReturnOrders(Long id);
    List<BorrowListDto> getBorrowList(Long id);
    BorrowListDetailsDto getBorrowListDetails(Long id, String bookName);
    Orders findByBorrowerIdAndBook(Long borrowerId,String bookName);
    Book findBookById(Long id);

}
