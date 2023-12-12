package com.unisys.trans.cps.middleware.utillities;

import com.unisys.trans.cps.middleware.models.entity.ContactQuery;
import com.unisys.trans.cps.middleware.utilities.ExcelExporter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExcelExportTest {




    @Test
    void export() throws IOException {

        LocalDateTime dateTime = LocalDateTime.now();
        
        ContactQuery qry = new ContactQuery();
        qry.setEmail("test@gmail.com");
        qry.setName("testName");
        qry.setPhone("98876610");
        qry.setCarrier("AC");
        qry.setDate(dateTime);
        qry.setFunctionDesc("FunctionDesc");
        qry.setProblemDesc("ProblemDesc");

        List<ContactQuery> queries = new ArrayList<>();
        queries.add(qry);

        ExcelExporter exp  = new ExcelExporter(queries);

        byte[] file = exp.export();

         assertNotNull(file);
        Assert.assertTrue(file.length>0);


    }
}
