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
        transactionFunctionAudit.setAwbNumber("123");
        transactionFunctionAudit.setBranchID(10010);
        transactionFunctionAudit.setCarrier("AC");
        transactionFunctionAudit.setConfNumber("");
        transactionFunctionAudit.setDocument("<ECIDRQ-INP><SVCHEADER><SVCVERSION>0</SVCVERSION><TRANSID>T2304187160505</TRANSID><USERID>CPSGUEST</USERID><STATION>HDQ</STATION><AGENT>299</AGENT></SVCHEADER><OPTION>P</OPTION><DRA-INP><OPTION><DIST-UNIT>M</DIST-UNIT><ORIGIN><STATION>YVR</STATION></ORIGIN><DEST><STATION>SYD</STATION></DEST><LAT><DATE>19APR23</DATE><TIME>0630</TIME></LAT><TOA><DATE>22Apr23</DATE><TIME>0630</TIME></TOA><REJS>N</REJS><WUNI>KG</WUNI><VUNI>MC</VUNI><SKIP-HEIRARCHY>Y</SKIP-HEIRARCHY><PROD1>PPR</PROD1><PROD2>PFH</PROD2><PROD3>AFH</PROD3></OPTION><SPACE><PIECES>1</PIECES><IND>E</IND><WGT>1</WGT><VOL>1</VOL><DVOL>1</DVOL></SPACE><PART-INFO><PARTICIPANT><NAME> </NAME><STATION>YVR</STATION><TYPE>F</TYPE><ACCNO>093225</ACCNO><ACCID> </ACCID></PARTICIPANT></PART-INFO><RATING-INFO><SHIPMENT-DATE>19APR23</SHIPMENT-DATE><COM-CLS> </COM-CLS></RATING-INFO></DRA-INP></ECIDRQ-INP>");
        transactionFunctionAudit.setErrorTxt("Q533: ALLOTMENT DOES NOT EXIST");
        transactionFunctionAudit.setEventDate(currentDate);
        transactionFunctionAudit.setFromPage("");
        transactionFunctionAudit.setHostError("Q533: ALLOTMENT DOES NOT EXIST");
        transactionFunctionAudit.setIpAddress("127.0.0.1");
        transactionFunctionAudit.setPortalFunction("ADVRATE");
        transactionFunctionAudit.setPortalIdentity("CPS");
        transactionFunctionAudit.setServerName("HSEAN230");
        transactionFunctionAudit.setStatus("F");
        transactionFunctionAudit.setSubFunction("INQUIRY");
        transactionFunctionAudit.setTxnStatus(" ");
        transactionFunctionAudit.setUserId("BiswalT");
        
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
        when(transactionErrorDescRepository.getAllTransactionErrorsData(Mockito.anyString(), Mockito.eq(currentDate), Mockito.eq(past30Date), Mockito.anyString()))
                .thenReturn(list);
        when(transactionErrorDescRepository.getAllTransactionErrorsCount(Mockito.anyString(), Mockito.eq(currentDate), Mockito.eq(past30Date), Mockito.anyString()))
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
