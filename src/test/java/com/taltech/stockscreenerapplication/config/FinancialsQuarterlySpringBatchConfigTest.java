package com.taltech.stockscreenerapplication.config;

import com.taltech.stockscreenerapplication.model.FinancialsQuarterly;
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
class FinancialsQuarterlySpringBatchConfigTest {

    @Autowired
    FinancialsQuarterlySpringBatchConfig financialsQuarterlySpringBatchConfig;

    @Test
    void financialsQuarterlyItemReader() throws Exception {

        int companiesCount = 16;

        FlatFileItemReader<FinancialsQuarterly> flatFileItemReader = financialsQuarterlySpringBatchConfig.financialsQuarterlyItemReader();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/data/FinancialsQuarterly.csv"));
        flatFileItemReader.setLineMapper(financialsQuarterlySpringBatchConfig.financialsQuarterlyLineMapper());
        flatFileItemReader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());


        int count = 0;
        while (flatFileItemReader.read() != null) {
            count++;
        }

        assertEquals(companiesCount, count);

        flatFileItemReader.close();
    }

    @Test
    void financialsQuarterlyLineMapper() {
    }
}