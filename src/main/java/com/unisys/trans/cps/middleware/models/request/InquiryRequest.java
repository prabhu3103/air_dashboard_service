package com.unisys.trans.cps.middleware.models.request;

import java.io.Serializable;

import lombok.Data;


@Data
public class InquiryRequest implements Serializable {


    private String date;


    private String carrier;
}
