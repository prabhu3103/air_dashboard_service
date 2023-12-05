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
    @Column(name = "NAME")
    private String name;

    @Id
    @Column(name = "phone")
    private String phone;

    @Column(name = "FUNCTION_DESC")
    private String functionDesc;

    @Column(name = "PROBLEM_DESC")
    private String problemDesc;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CREATIONDATE")
    private LocalDateTime date;

    @Column(name = "TOCARRIER")
    private String carrier;

}
