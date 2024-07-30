//package kr.or.kosa.cmsplusbatch.config;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import kr.or.kosa.cmsplusbatch.batch.reader.QuerydslItemReader;
//import kr.or.kosa.cmsplusbatch.batch.service.V2InvoiceProcessorService;
//import kr.or.kosa.cmsplusbatch.batch.service.V2InvoiceWriterService;
//import kr.or.kosa.cmsplusbatch.batch.dto.BillingQueryDto;
//import kr.or.kosa.cmsplusbatch.domain.billing.repository.BillingRepository;
//import kr.or.kosa.cmsplusbatch.domain.contract.repository.ContractRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobExecutionListener;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.time.LocalDate;
//
//@Configuration
//@EnableBatchProcessing
//@RequiredArgsConstructor
//public class V2BatchConfig {
//
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    private final BillingRepository billingRepository;
//    private final ContractRepository contractRepository;
//    private final JPAQueryFactory queryFactory;
//
//    @Bean
//    public Job sendInvoiceJob() {
//        return new JobBuilder("sendInvoiceJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(sendInvoiceStep())
//                .listener(jobExecutionListener())
//                .build();
//    }
//
//    @Bean
//    public Step sendInvoiceStep() {
//        return new StepBuilder("sendInvoiceStep", jobRepository)
//                .<BillingQueryDto, BillingQueryDto>chunk(1000, transactionManager)
//                .reader(reader())
//                .processor(processor())
//                .writer(writer())
//                .build();
//    }
//
//    @Bean
//    public QuerydslItemReader reader() {
//        return new QuerydslItemReader(queryFactory, LocalDate.now(), 100);
//    }
//
//    @Bean
//    public V2InvoiceProcessorService processor() {
//        return new V2InvoiceProcessorService();
//    }
//
//    @Bean
//    public V2InvoiceWriterService writer() {
//        return new V2InvoiceWriterService(billingRepository, contractRepository);
//    }
//
//    @Bean
//    public JobExecutionListener jobExecutionListener() {
//        return new JobExecutionListener() {
//            @Override
//            public void beforeJob(JobExecution jobExecution) {
//                reader().setLastId(0);
//            }
//
//            @Override
//            public void afterJob(JobExecution jobExecution) {
//                // 작업 완료 후 필요한 로직
//            }
//        };
//    }
//}
