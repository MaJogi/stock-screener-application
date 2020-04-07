package com.taltech.stockscreenerapplication.config;

import com.taltech.stockscreenerapplication.model.FinancialsDaily;
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
public class FinancialsDailySpringBatchConfig {

    @Bean
    public Job financialsDailyJob(JobBuilderFactory jobBuilderFactory,
                                  StepBuilderFactory stepBuilderFactory,
                                  ItemReader<FinancialsDaily> itemReader,
                                  ItemWriter<FinancialsDaily> itemWriter
    ) {

        Step step = stepBuilderFactory.get("ETL-file-laod")
                .<FinancialsDaily, FinancialsDaily>chunk(100)
                .reader(itemReader)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public FlatFileItemReader<FinancialsDaily> financialsDailyItemReader() {

        FlatFileItemReader<FinancialsDaily> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/data/FinancialsDaily.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(financialsDailyLineMapper());
        return flatFileItemReader;

    }

    @Bean
    public LineMapper<FinancialsDaily> financialsDailyLineMapper() {
        DefaultLineMapper<FinancialsDaily> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("ticker_id", "div_yield", "mkt_cap", "p_e", "price_rev", "p_b", "ev_ebitda", "ev", "chg", "weekly_perf", "monthly_perf",
                "three_month_perf", "six_month_perf", "ytd_perf", "yearly_perf", "one_y_beta", "volatility");

        BeanWrapperFieldSetMapper<FinancialsDaily> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(FinancialsDaily.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
