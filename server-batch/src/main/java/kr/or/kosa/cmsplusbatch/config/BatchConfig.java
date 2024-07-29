package kr.or.kosa.cmsplusbatch.config;

import kr.or.kosa.cmsplusbatch.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusbatch.domain.billing.repository.BillingRepository;
import kr.or.kosa.cmsplusbatch.global.InvoiceProcessor;
import kr.or.kosa.cmsplusbatch.global.InvoiceWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final BillingRepository billingRepository;

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
                .<Billing, Billing>chunk(100, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    /*
    * Chunk Size는 한번에 처리될 트랜잭션 단위를 얘기하며, Page Size는 한번에 조회할 Item의 양
    * PageSize가 10이고, ChunkSize가 50이라면 ItemReader에서 Page 조회가 5번 일어나면 1번의 트랜잭션이 발생하여 Chunk가 처리됨
    * 한번의 트랜잭션 처리를 위해 5번의 쿼리 조회가 발생하기 때문에 성능상 이슈가 발생할 수 있음
    * 공식문서: Setting a fairly large page size and using a commit interval that matches the page size should provide better performance. (상당히 큰 페이지 크기를 설정하고 페이지 크기와 일치하는 커미트 간격을 사용하면 성능이 향상됩니다.)
    * */
    @Bean
    public RepositoryItemReader<Billing> reader() {
        RepositoryItemReader<Billing> reader = new RepositoryItemReader<>();
        reader.setRepository(billingRepository);
        reader.setMethodName("findAllByBillingDateAndMemberInvoiceSendMethod");
        reader.setArguments(Arrays.asList(LocalDate.now()));
        reader.setPageSize(100);
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        return reader;
    }

    @Bean
    public InvoiceProcessor processor() {
        return new InvoiceProcessor();
    }

    @Bean
    public InvoiceWriter writer() {
        return new InvoiceWriter(billingRepository);
    }
}