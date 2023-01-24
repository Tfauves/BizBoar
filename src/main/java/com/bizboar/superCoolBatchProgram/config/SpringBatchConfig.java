package com.bizboar.superCoolBatchProgram.config;

import com.bizboar.superCoolBatchProgram.domain.BankLoanData;
import com.bizboar.superCoolBatchProgram.repositories.BankLoanDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    private BankLoanDataRepository bankLoanDataRepository;

    @Bean
    public FlatFileItemReader<BankLoanData> reader() {
        FlatFileItemReader<BankLoanData> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/data/SBAnational.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    private LineMapper<BankLoanData> lineMapper() {
        DefaultLineMapper<BankLoanData> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("loannr_chkdgt ", "name", "city", "state", "zip", "bank",
                "bank_state", "naics", "approval_date", "approval_fy", "term", "no_emp", "new_exist",
                "create_job", "retained_job", "franchise_code", "urban_rural", "rev_line_cr",
                "low_doc", "chg_off_date", "disbursement_date", "disbursement_gross", "balance_gross",
                "mis_status", "chg_off_prin_gr", "gr_appv", "sba_appv"
        );

        BeanWrapperFieldSetMapper<BankLoanData> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(BankLoanData.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

    @Bean
    public BankLoanDataProcessor processor() {
        return new BankLoanDataProcessor();
    }

    @Bean
    public RepositoryItemWriter<BankLoanData> writer() {
        RepositoryItemWriter<BankLoanData> writer = new RepositoryItemWriter<>();
        writer.setRepository(bankLoanDataRepository);
        writer.setMethodName("save");
        return writer;
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("csv-step").<BankLoanData, BankLoanData> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job batchJob() {
        return jobBuilderFactory.get("bankLoanData")
                .flow(step1())
                .end()
                .build();
    }




}
