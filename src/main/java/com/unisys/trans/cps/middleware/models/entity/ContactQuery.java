package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table(name = "CONTACTQUERY")
@Entity
@IdClass(ContactQueryKey.class)
public class ContactQuery  implements Serializable {

    @Id
    @Column(name = "EMAIL")
    private String email;

    @Id
    @Column(name = "CREATIONDATE")
    private LocalDateTime date;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "FUNCTION_DESC")
    private String functionDesc;

    @Column(name = "PROBLEM_DESC")
    private String problemDesc;

    @Column(name = "TOCARRIER")
    private String carrier;

}
