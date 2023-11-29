package com.unisys.trans.cps.middleware.services;

import com.unisys.trans.cps.middleware.models.response.BranchRequestDTO;
import com.unisys.trans.cps.middleware.repository.BranchRequestsRepository;
import com.unisys.trans.cps.middleware.services.branchrequestsservice.BranchRequestsService;
import com.unisys.trans.cps.middleware.services.branchrequestsservice.BranchRequestsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BranchRequestServiceImplTest {

    @InjectMocks
    private BranchRequestsServiceImpl serviceImpl;

    @Mock
    private BranchRequestsRepository repository;

    @Mock
    private BranchRequestsService service;

    @Test
    void getBranchrequestTest(){

        List<String> value = new ArrayList<>();
        value.add("1");
        value.add("2");
        value.add("0");

        when(repository.getStatus(anyString())).thenReturn(value);
        BranchRequestDTO response = serviceImpl.getBranchRequests(anyString());
        assertNotNull(response);
    }
}
