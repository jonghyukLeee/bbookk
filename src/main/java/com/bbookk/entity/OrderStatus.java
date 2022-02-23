package com.bbookk.entity;

import lombok.Getter;

public enum OrderStatus {
    REQUESTED,LEND;

    public String toString(OrderStatus status)
    {
        return status.equals(OrderStatus.REQUESTED) ? "요청중" : "대여중";
    }
}
