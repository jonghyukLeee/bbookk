package com.bbookk.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BooksOfMonth {

    @Id
    @GeneratedValue
    @Column(name = "bom_id")
    private Long id;

    private String imgSource;
    private String bookName;
    private LocalDateTime registerTime;
    private int registerCnt;

    public void addCnt()
    {
        this.registerCnt += 1;
    }
    public BooksOfMonth(String imgSource, String bookName)
    {
        this.imgSource = imgSource;
        this.bookName = bookName;
        this.registerTime = LocalDateTime.now();
        this.registerCnt = 1;
    }
}
