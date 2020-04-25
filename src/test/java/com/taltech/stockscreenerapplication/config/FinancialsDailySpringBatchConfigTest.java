package com.taltech.stockscreenerapplication.config;

import com.taltech.stockscreenerapplication.model.FinancialsDaily;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBatchTest
@SpringBootTest
@RunWith(SpringRunner.class)
class FinancialsDailySpringBatchConfigTest {

    @Autowired
    FinancialsDailySpringBatchConfig financialsDailySpringBatchConfig;

    @Test
    void financialsDailyItemReader() throws Exception {

        int companiesCount = 16;

        FlatFileItemReader<FinancialsDaily> flatFileItemReader = financialsDailySpringBatchConfig.financialsDailyItemReader();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/data/FinancialsDaily.csv"));
        flatFileItemReader.setLineMapper(financialsDailySpringBatchConfig.financialsDailyLineMapper());
        flatFileItemReader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());


        int count = 0;
        while (flatFileItemReader.read() != null) {
            count++;
        }

        assertEquals(companiesCount, count);

        flatFileItemReader.close();
    }

    @Test
    void financialsDailyLineMapper() {
    }
}