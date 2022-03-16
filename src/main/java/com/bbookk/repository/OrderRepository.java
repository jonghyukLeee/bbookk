package com.bbookk.repository;

import com.bbookk.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Long> ,OrderRepositoryCustom{
}
