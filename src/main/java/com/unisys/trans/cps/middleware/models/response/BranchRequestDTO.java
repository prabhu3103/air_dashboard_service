package com.unisys.trans.cps.middleware.models.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchRequestDTO {

    private  int approved;
    private  int pending;
    private  int rejected;
}
