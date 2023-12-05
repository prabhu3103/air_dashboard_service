package com.unisys.trans.cps.middleware.models.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Data
public class InquiryRequest implements Serializable {


    private String date;


    private String carrier;
}
