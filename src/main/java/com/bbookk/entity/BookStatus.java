package com.bbookk.entity;

public enum BookStatus {
    NONE,REQUESTED,RENTED,END;

    public String toString(BookStatus status)
    {
        switch(status)
        {
            case NONE:
            {
                return "대여가능";
            }
            case REQUESTED:
            {
                return "대여요청중";
            }
            case RENTED:
            {
                return "대여중";
            }
            default:
            {
                return "반납대기";
            }
        }
    }
}
