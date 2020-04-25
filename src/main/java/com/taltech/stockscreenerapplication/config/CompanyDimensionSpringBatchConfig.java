package com.taltech.stockscreenerapplication.config;

import com.taltech.stockscreenerapplication.model.CompanyDimension;
import com.taltech.stockscreenerapplication.model.FinancialsDaily;
import com.taltech.stockscreenerapplication.model.FinancialsQuarterly;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class CompanyDimensionSpringBatchConfig {

    @Bean
    public Job companyObjectJob(JobBuilderFactory jobBuilderFactory,
                                StepBuilderFactory stepBuilderFactory,
                                ItemReader<CompanyDimension> companyDimensionItemReader,
                                ItemWriter<CompanyDimension> companyDimensionItemWriter,
                                ItemReader<FinancialsDaily> financialsDailyItemReader,
                                ItemWriter<FinancialsDaily> financialsDailyItemWriter,
                                ItemReader<FinancialsQuarterly> financialsQuarterlyItemReader,
                                ItemWriter<FinancialsQuarterly> financialsQuarterlyItemWriter
    ) {

        Step companyDimensionStep = stepBuilderFactory.get("ETL-file-load")
                .<CompanyDimension, CompanyDimension>chunk(100)
                .reader(companyDimensionItemReader)
                .writer(companyDimensionItemWriter)
                .build();

        Step financialsDailyStep = stepBuilderFactory.get("ETL-file-load")
                .<FinancialsDaily, FinancialsDaily>chunk(100)
                .reader(financialsDailyItemReader)
                .writer(financialsDailyItemWriter)
                .build();

        Step financialsQuarterlyStep = stepBuilderFactory.get("ETL-file-load")
                .<FinancialsQuarterly, FinancialsQuarterly>chunk(100)
                .reader(financialsQuarterlyItemReader)
                .writer(financialsQuarterlyItemWriter)
                .build();

        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(companyDimensionStep)
                .next(financialsDailyStep)
                .next(financialsQuarterlyStep)
                .build();
    }

    @Bean
    public FlatFileItemReader<CompanyDimension> companyDimensionItemReader() {

        FlatFileItemReader<CompanyDimension> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/data/CompanyDimension.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(companyDimensionLineMapper());
        return flatFileItemReader;

    }

    @Bean
    public LineMapper<CompanyDimension> companyDimensionLineMapper() {
        DefaultLineMapper<CompanyDimension> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("ticker_id", "name", "employees", "sector", "industry");

        BeanWrapperFieldSetMapper<CompanyDimension> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CompanyDimension.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
