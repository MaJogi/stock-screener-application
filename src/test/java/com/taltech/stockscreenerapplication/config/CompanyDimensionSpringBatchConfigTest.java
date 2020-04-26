package com.taltech.stockscreenerapplication.config;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.FinancialsDaily;
import com.taltech.stockscreenerapplication.model.FinancialsQuarterly;
import com.taltech.stockscreenerapplication.repository.CompanyDimensionRepository;
import com.taltech.stockscreenerapplication.repository.FinancialsDailyRepository;
import com.taltech.stockscreenerapplication.repository.FinancialsQuarterlyRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@SpringBatchTest
@RunWith(SpringRunner.class)
class CompanyDimensionSpringBatchConfigTest {

    @Autowired
    CompanyDimensionSpringBatchConfig companyDimensionSpringBatchConfig;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    CompanyDimensionRepository companyDimensionRepository;

    @Autowired
    FinancialsDailyRepository financialsDailyRepository;

    @Autowired
    FinancialsQuarterlyRepository financialsQuarterlyRepository;

    @Test
    void companyObjectJobTest() throws Exception {

        int companiesCount = 16;

        companyDimensionRepository.deleteAll();
        financialsDailyRepository.deleteAll();
        financialsQuarterlyRepository.deleteAll();

        List<CompanyDimension> companyDimensions = companyDimensionRepository.findAll();
        Assert.assertEquals(0, companyDimensions.size());
        List<FinancialsDaily> financialsDailies = financialsDailyRepository.findAll();
        Assert.assertEquals(0, financialsDailies.size());
        List<FinancialsQuarterly> financialsQuarterlies = financialsQuarterlyRepository.findAll();
        Assert.assertEquals(0, financialsQuarterlies.size());

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());

        companyDimensions = companyDimensionRepository.findAll();
        Assert.assertEquals(companyDimensions.size(), companiesCount);
        financialsDailies = financialsDailyRepository.findAll();
        Assert.assertEquals(financialsDailies.size(), companiesCount);
        financialsQuarterlies = financialsQuarterlyRepository.findAll();
        Assert.assertEquals(financialsQuarterlies.size(), companiesCount);
    }
}