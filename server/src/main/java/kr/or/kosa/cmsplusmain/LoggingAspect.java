package kr.or.kosa.cmsplusmain;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("@annotation(kr.or.kosa.cmsplusmain.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 어노테이션에서 서비스 이름 추출
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogExecutionTime logExecutionTime = signature.getMethod().getAnnotation(LogExecutionTime.class);
        String serviceName = logExecutionTime.value();
        String methodName = signature.getMethod().getName();

        long startTime = System.currentTimeMillis();
        log.info("Started {} : {}", serviceName, methodName);

        Object proceed = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("Completed {} : {} in [{} ms]", serviceName, methodName, duration);

        return proceed;
    }
}
