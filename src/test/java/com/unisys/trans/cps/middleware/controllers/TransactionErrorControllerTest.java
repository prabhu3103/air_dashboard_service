package com.unisys.trans.cps.middleware.controllers;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisys.trans.cps.middleware.exception.CpsException;
import com.unisys.trans.cps.middleware.models.ResponseEntity;
import com.unisys.trans.cps.middleware.models.entity.TransactionFunctionAudit;
import com.unisys.trans.cps.middleware.models.request.TransactionRequest;
import com.unisys.trans.cps.middleware.models.response.MessageEntry;
import com.unisys.trans.cps.middleware.models.response.TransactionData;
import com.unisys.trans.cps.middleware.models.response.TransactionErrorCount;
import com.unisys.trans.cps.middleware.models.response.TransactionErrorData;
import com.unisys.trans.cps.middleware.repository.TransactionErrorRepository;
import com.unisys.trans.cps.middleware.services.transactionerrorservice.TransactionErrorService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TransactionErrorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private TransactionErrorController transactionErrorDescController;

    @MockBean
    TransactionErrorRepository transactionErrorDescRepository;
    
    @Mock
    private TransactionErrorService transactionErrorDescService;

    @Mock
    TransactionData transactionData;
    
    String portalFunction;
 
    @Mock
    TransactionErrorData transactionErrorData;

    @Mock
    List<MessageEntry> errorList;
    
    @Mock
    MessageEntry messageEntry;
    @Mock
    ResponseEntity<TransactionData> response;
    
    @Mock
    TransactionErrorCount transactionErrorCount;
    @Test
    void getMyCarriersRequestedTest() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setCarrier("AC");
        request.setDate("2023-01-01 12:00:00.000");
        request.setPortalFunction("ADVRATE");
        String getDate = "2023-01-01 12:00:00.000"; 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime currentDate = LocalDateTime.parse(getDate, formatter);
        LocalDateTime past30Date = currentDate.minus(30, ChronoUnit.DAYS);
 
        TransactionFunctionAudit transactionFunctionAudit=new TransactionFunctionAudit();
        transactionFunctionAudit.setAuditID(1);
        transactionFunctionAudit.setCarrier("AC");
        transactionFunctionAudit.setEventDate(currentDate);
        transactionFunctionAudit.setHostError("Q533: ALLOTMENT DOES NOT EXIST");
        transactionFunctionAudit.setPortalFunction("ADVRATE");
        transactionFunctionAudit.setStatus("F");
        transactionFunctionAudit.setTxnStatus(" ");
        
        List<TransactionFunctionAudit>  list=new ArrayList<>();
       list.add(transactionFunctionAudit);
       
       TransactionErrorData transactionErrorData=new  TransactionErrorData();
       transactionErrorData.setErrorCount(4);
       transactionErrorData.setErrorDescription("Host Reply Time Out");
       transactionErrorData.setErrorPercent(20.0f);
       
       List<TransactionErrorData> errorDataList = new ArrayList<>(); 
       errorDataList.add(transactionErrorData);
       
       MessageEntry messageEntry=new MessageEntry();
       messageEntry.setCode("1");
       messageEntry.setMessageType("Msg");
       messageEntry.setText("Message");
       
       List<MessageEntry> errorList =new ArrayList<>();
       errorList.add(messageEntry);
       
       ResponseEntity<TransactionData> response=new ResponseEntity<>();
       response.setSuccessFlag(true);
       response.setResponse(transactionData);
       response.setErrorList(errorList);
       
       TransactionData transactionData=new TransactionData();
       transactionData.setErrorCount(12);
       transactionData.setErrorCountPercent(34.5f);
       transactionData.setNormalCountPercent(45.5f);
       transactionData.setNormalCount(23);
       transactionData.setTotalTransaction(45);
       
       transactionData.setErrorTransactions(errorDataList);
      
       TransactionErrorCount transactionErrorCount=new TransactionErrorCount();
       transactionErrorCount.setErrorCount(3);
       transactionErrorCount.setPortalFunction(portalFunction);
       
       
       List<TransactionErrorCount> transactionErrorCountList=new ArrayList<>();
       transactionErrorCountList.add(transactionErrorCount);
        when(transactionErrorDescRepository.getAllTransactionErrors(Mockito.anyString(), Mockito.eq(currentDate), Mockito.eq(past30Date)))
        .thenReturn(list);
       when(transactionErrorDescService.getTransactionErrors(request)).thenThrow(new CpsException("Test CpsException"));
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/airline-dashboard/transaction-error-count")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.successFlag").value(true));
     
 
    }
  
}
