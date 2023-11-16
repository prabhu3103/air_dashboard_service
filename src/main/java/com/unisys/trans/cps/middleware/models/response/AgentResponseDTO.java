package com.unisys.trans.cps.middleware.models.response;

import lombok.Data;

import java.util.List;

@Data
public class AgentResponseDTO {

    private Long newAccount;

    List<TopAgentsResponseDTO>  topAgentsResponseDTOList;

}
