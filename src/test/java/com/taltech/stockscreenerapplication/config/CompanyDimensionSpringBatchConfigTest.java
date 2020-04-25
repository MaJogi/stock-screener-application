package com.taltech.stockscreenerapplication.config;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
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
class CompanyDimensionSpringBatchConfigTest {

    @Autowired
    CompanyDimensionSpringBatchConfig companyDimensionSpringBatchConfig;

    @Test
    void companyObjectJob() {
    }

    @Test
    void companyDimensionItemReader() throws Exception {

        int companiesCount = 16;

        FlatFileItemReader<CompanyDimension> flatFileItemReader = companyDimensionSpringBatchConfig.companyDimensionItemReader();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/data/CompanyDimension.csv"));
        flatFileItemReader.setLineMapper(companyDimensionSpringBatchConfig.companyDimensionLineMapper());
        flatFileItemReader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());


        int count = 0;
        while (flatFileItemReader.read() != null) {
            count++;
        }

        assertEquals(companiesCount, count);

        flatFileItemReader.close();
    }

    @Test
    void companyDimensionLineMapper() {
    }
}