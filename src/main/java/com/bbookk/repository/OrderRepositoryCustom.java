package com.bbookk.repository;

import com.bbookk.repository.dto.RequestsDto;

import java.util.List;

public interface OrderRepositoryCustom {
    List<RequestsDto> getRequestedOrders(Long id);
}
