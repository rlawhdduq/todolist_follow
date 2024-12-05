package todolist.follow.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLSyntaxErrorException;

import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.context.ApplicationContextException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.QueryTimeoutException;
import jakarta.transaction.TransactionRequiredException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private void logPrinter(String logType, Exception ex)
    {
        log.info(logType);
        log.error("Error occurred", ex);
        // for( StackTraceElement element : ex.getStackTrace() )
        // {
        //     log.info("Class : " + element.getClassName());
        //     log.info("Method : " + element.getMethodName());
        //     log.info("Line : " + element.getLineNumber());
        // }
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logPrinter("Exception Err", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록되지 않은 예외 발생");
    }

    // DB 예외
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        logPrinter("DataIntegerityViolation Err", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("유니크 제약조건");
    }
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handleEntityExistsException(EntityExistsException ex) {
        logPrinter("EntityExists Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("중복삽입");
    }
    @ExceptionHandler(TransactionRequiredException.class)
    public ResponseEntity<String> handleTransactionRequiredException(TransactionRequiredException ex) {
        logPrinter("Transaction Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("트랜잭션 미설정");
    }
    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<String> handleOptimisticLockException(OptimisticLockException ex) {
        logPrinter("OptimisticLock Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("버전관리 락 발생");
    }
    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<String> handleJpaSystemException(JpaSystemException ex) {
        logPrinter("JpaSystem Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("JPA 시스템 레벨 예외발생");
    }
    @ExceptionHandler(QueryTimeoutException.class)
    public ResponseEntity<String> handleQueryTimeoutException(QueryTimeoutException ex) {
        logPrinter("QueryTimeout Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DB Query Timeout");
    }
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> handleTransactionSystemException(TransactionSystemException ex) {
        logPrinter("TransactionSystem Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("트랜잭션 처리 중 시스템 오류 발생");
    }
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<String> handleSQLSyntaxErrorException(SQLSyntaxErrorException ex) {
        logPrinter("SQLSyntaxError Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("쿼리 문법 오류");
    }
    
    // 내부 구현 및 다른 서비스와 통신 관련 예외
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        logPrinter("NullPointer Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Null value encountered.");
    }
    @ExceptionHandler(NoSuchBeanDefinitionException.class)
    public ResponseEntity<String> handleNoSuchBeanDefinitionException(NoSuchBeanDefinitionException ex) {
        logPrinter("NoSuchBeanDefinition Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록되지 않은 빈 참조");
    }
    @ExceptionHandler(KafkaException.class)
    public ResponseEntity<String> handleKafkaException(KafkaException ex) {
        logPrinter("Kafka Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("카프카 통신에러 발생");
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException ex) {
        logPrinter("HttpClientError Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Rest 4XX 에러응답");
    }
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleHttpServerErrorException(HttpServerErrorException ex) {
        logPrinter("HttpServerError Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Rest 5XX 에러응답");
    }
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<String> handleConnectException(ConnectException ex) {
        logPrinter("Connect Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("외부 연결 실패");
    }
    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<String> handleSocketTimeoutException(SocketTimeoutException ex) {
        logPrinter("SocketTimeoutException Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("외부 연결 시간초과");
    }
    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<String> handleUnknownHostException(UnknownHostException ex) {
        logPrinter("UnknownHost Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("잘못된 URL");
    }
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException ex) {
        logPrinter("WebClientResponse Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("외부 연결 처리과정에서(WebClient) 예외 응답 발생");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        logPrinter("WebClientResponse Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("외부 연결 처리과정에서(WebClient) 예외 응답 발생");
    }
    
    // Gradle 예외
    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseEntity<String> handleClassNotFoundException(ClassNotFoundException ex) {
        logPrinter("ClassNotFound Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("잘못된 클래스 호출 시도");
    }
    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<String> handleNoSuchMethodError(NoSuchMethodException ex) {
        logPrinter("NoSuchMethod Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("특정 메서드 누락(or 의존성 버전 불일치)");
    }
    
    // Application
    @ExceptionHandler(BeanCreationException.class)
    public ResponseEntity<String> handleBeanCreationException(BeanCreationException ex) {
        logPrinter("BeanCreation Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("초기화 중 빈 생성 실패");
    }
    @ExceptionHandler(ApplicationContextException.class)
    public ResponseEntity<String> handleApplicationContextException(ApplicationContextException ex) {
        logPrinter("ApplicationContext Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("애플리케이션 초기화 실패");
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handleConfigurationPropertiesBindingException(BindException ex) {
        logPrinter("ConfigurationPropertiesBinding Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("잘못된 형식의 구성 값 주입");
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        logPrinter("IllegalState Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("애플리케이션 상태 예외");
    }
    
    // File
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex) {
        logPrinter("IO Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("입출력 예외");
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFoundException(FileNotFoundException ex) {
        logPrinter("FileNotFound Err : ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 찾기 실패");
    }
    
    // 커스텀 예외 처리
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}