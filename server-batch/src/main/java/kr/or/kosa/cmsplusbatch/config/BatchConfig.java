package kr.or.kosa.cmsplusbatch.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kosa.cmsplusbatch.batch.reader.ZeroOffsetItemReader;
import kr.or.kosa.cmsplusbatch.batch.service.InvoiceProcessorService;
import kr.or.kosa.cmsplusbatch.batch.service.InvoiceWriterService;
import kr.or.kosa.cmsplusbatch.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusbatch.domain.billing.repository.BillingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;


@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final BillingRepository billingRepository;
    private final JPAQueryFactory queryFactory;

    @Bean
    public Job sendInvoiceJob() {
        return new JobBuilder("sendInvoiceJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(sendInvoiceStep())
                .build();
    }

    @Bean
    public Step sendInvoiceStep() {
        return new StepBuilder("sendInvoiceStep", jobRepository)
                .<Billing, Billing>chunk(10000, transactionManager)
                .reader(billingItemReader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ZeroOffsetItemReader billingItemReader() {
        LocalDate billingDate = LocalDate.now(); // 원하는 날짜로 설정
        return new ZeroOffsetItemReader(queryFactory, billingDate, 10000); // chunkSize 설정; page
    }

    @Bean
    public InvoiceProcessorService processor() {
        return new InvoiceProcessorService();
    }

    @Bean
    public InvoiceWriterService writer() {
        return new InvoiceWriterService(billingRepository);
    }
}