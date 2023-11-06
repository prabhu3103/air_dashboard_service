package com.unisys.trans.cps.middleware.models.entity;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CommodityId {

    private String carrier;

    private String code;

    private String description;

}
