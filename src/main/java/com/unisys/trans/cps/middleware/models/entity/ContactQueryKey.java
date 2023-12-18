package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
public class ContactQueryKey implements Serializable {

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "email")
    private String email;

    @Column(name = "CREATIONDATE")
    private LocalDateTime date;


}
