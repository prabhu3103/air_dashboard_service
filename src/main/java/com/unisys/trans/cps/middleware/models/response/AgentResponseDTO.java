package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

import java.util.List;

@Data
public class AgentResponseDTO {

    private int newAccount;

    List<TopAgentsResponseDTO>  topAgentsResponseDTOList;

}
