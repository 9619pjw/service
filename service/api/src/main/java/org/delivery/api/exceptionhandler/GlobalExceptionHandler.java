package org.delivery.api.exceptionhandler;
// 예상치 못한 에러 처리

import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // 예외를 모아서 처리
@Order(value = Integer.MAX_VALUE) // 가장 마지막에 실행 적용함.
public class GlobalExceptionHandler {

    // 프로그램 내에서 발생하는 예상 못한 모든 예외를 받아온다.
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception (
            Exception exception
    ){
        // 에러 발생 위치 로그로 남김.
        log.error("",exception);

        // 서버 에러 처리
        return ResponseEntity
                .status(500)
                .body(Api.ERROR(ErrorCode.SERVER_ERROR));
    }
}
