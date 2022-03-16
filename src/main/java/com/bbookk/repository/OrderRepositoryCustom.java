package com.bbookk.repository;

import com.bbookk.entity.Orders;
import com.bbookk.repository.dto.RequestedBooksDto;

import java.util.List;

public interface OrderRepositoryCustom {
    List<RequestedBooksDto> getRequestedOrders(Long id);
}
