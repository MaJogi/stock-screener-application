package com.taltech.stockscreenerapplication.config;

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
public class FinancialsQuarterlySpringBatchConfig {

    @Bean
    public Job financialsQuarterlyJob(JobBuilderFactory jobBuilderFactory,
                                      StepBuilderFactory stepBuilderFactory,
                                      ItemReader<FinancialsQuarterly> itemReader,
                                      ItemWriter<FinancialsQuarterly> itemWriter
    ) {

        Step step = stepBuilderFactory.get("ETL-file-laod")
                .<FinancialsQuarterly, FinancialsQuarterly>chunk(100)
                .reader(itemReader)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public FlatFileItemReader<FinancialsQuarterly> financialsQuarterlyItemReader() {

        FlatFileItemReader<FinancialsQuarterly> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/data/FinancialsQuarterly.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(financialsQuarterlyLineMapper());
        return flatFileItemReader;

    }

    @Bean
    public LineMapper<FinancialsQuarterly> financialsQuarterlyLineMapper() {
        DefaultLineMapper<FinancialsQuarterly> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("ticker_id", "current_ratio", "debt_to_equity", "net_debt", "quick_ratio", "assets", "debt", "current_assets", "eps_fy", "eps_ttm", "eps_diluted", "ebitda",
                "gross_profit_mrq", "gross_profit_fy", "revenue", "eps_diluted_fy", "annual_revenue", "income", "gross_mrq",
                "operating_mrq", "pretax_mrq", "net_mrq", "div_paid", "div_per_share", "roa", "roe", "shares");

        BeanWrapperFieldSetMapper<FinancialsQuarterly> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(FinancialsQuarterly.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
