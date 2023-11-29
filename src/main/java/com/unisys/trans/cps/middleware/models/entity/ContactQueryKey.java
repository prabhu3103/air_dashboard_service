package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class ContactQueryKey implements Serializable {

    @Column(name = "phone")
    private String phone;

    @Column(name = "NAME")
    private String name;
    
}
