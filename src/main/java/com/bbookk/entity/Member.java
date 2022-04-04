package com.bbookk.entity;

import com.bbookk.controller.form.AddressForm;
import com.bbookk.controller.form.ModifyForm;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String loginId;
    private String password;

    @Size(max = 11)
    private String phone;

    public Member(String name, String loginId, String password, String phone, Address address) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.cash = 500;
        this.role = Role.ROLE_MEMBER; // default role
    }

    @Builder
    public Member(String name, String loginId) {
        this.name = name;
        this.loginId = loginId;
        this.cash = 500;
        this.role = Role.ROLE_MEMBER;
    }

    @Embedded
    private Address address;

    private long rating;

    private int cash;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "member",orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "member",orphanRemoval = true)
    private List<Orders> orders = new ArrayList<>();

    public void setAdmin()
    {
        this.role = Role.ROLE_ADMIN;
    }

    public void addCash(int amount)
    {
        this.cash += amount;
    }

    public void subCash(int amount)
    {
        this.cash -= amount;
    }

    public void setAddress(AddressForm form)
    {
        this.address = new Address(form.getSi(),form.getGu(),form.getDong());
    }

    public void modify(ModifyForm form) {
        this.name = form.getName();
        this.phone = form.getPhone();
        this.address = new Address(form.getSi(),form.getGu(),form.getDong());
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }
}
