package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "PRODUCTTYPE")
@IdClass(ProductTypeId.class)
@Entity
public class ProductType {
    @Id
    @Column(name = "AIRLINE")
    private  String airline;
    @Id
    @Column(name = "PRODUCTTYPE")
    private String productType;

    @Column(name = "DESCRIPTION")
    private String description;





}
