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

        Step companyDimensionStep = stepBuilderFactory.get("Company Dimension")
                .<CompanyDimension, CompanyDimension>chunk(100)
                .reader(companyDimensionItemReader)
                .writer(companyDimensionItemWriter)
                .build();

        Step financialsDailyStep = stepBuilderFactory.get("Financials Daily")
                .<FinancialsDaily, FinancialsDaily>chunk(100)
                .reader(financialsDailyItemReader)
                .writer(financialsDailyItemWriter)
                .build();

        Step financialsQuarterlyStep = stepBuilderFactory.get("Financials Quarterly")
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

        return createItemReader(CompanyDimension.class, "src/main/resources/data/CompanyDimension.csv");

    }

    @Bean
    public FlatFileItemReader<FinancialsDaily> financialsDailyItemReader() {

        return createItemReader(FinancialsDaily.class, "src/main/resources/data/FinancialsDaily.csv");
    }

    @Bean
    public FlatFileItemReader<FinancialsQuarterly> financialsQuarterlyItemReader() {

        return createItemReader(FinancialsQuarterly.class, "src/main/resources/data/FinancialsQuarterly.csv");
    }

    public <T> FlatFileItemReader<T> createItemReader(Class<T> type, String filePath) {

        FlatFileItemReader<T> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource(filePath));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(createLineMapper(type));
        return flatFileItemReader;
    }

    public <T> LineMapper<T> createLineMapper(Class<T> type) {
        DefaultLineMapper<T> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);

        if (type == CompanyDimension.class) {
            lineTokenizer.setNames("ticker_id", "name", "employees", "sector", "industry");
        } else if (type == FinancialsDaily.class) {
            lineTokenizer.setNames("ticker_id", "price", "div_yield", "mkt_cap", "p_e", "price_rev", "p_b", "ev_ebitda",
                    "ev", "chg", "weekly_perf", "monthly_perf", "three_month_perf", "six_month_perf", "ytd_perf",
                    "yearly_perf", "one_y_beta", "volatility");
        } else if (type == FinancialsQuarterly.class) {
            lineTokenizer.setNames("ticker_id", "current_ratio", "debt_to_equity", "net_debt", "quick_ratio", "assets",
                    "debt", "current_assets", "eps_fy", "eps_ttm", "eps_diluted_ttm", "ebitda", "gross_profit_mrq",
                    "gross_profit_fy", "revenue", "eps_diluted_fy", "annual_revenue", "income", "gross_mrq",
                    "operating_mrq", "pretax_mrq", "net_mrq", "div_paid", "div_per_share", "roa", "roe", "shares");
        }


        BeanWrapperFieldSetMapper<T> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(type);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
