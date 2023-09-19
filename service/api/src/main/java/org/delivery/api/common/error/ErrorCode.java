package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeIfs {

    OK(200 , 200 , "성공"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 400, "잘못된 요청"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 500, "서버 에러"),

    NULL_POINT(HttpStatus.INTERNAL_SERVER_ERROR.value(), 512, "Null point")

    ;

    private final Integer httpStatusCode; // 상응하는 httpStatusCode
    private final Integer errorCode;      // Internal 에러 코드
    private final String description;


  /* @Getter 어노테이션을 사용하여 필요없어짐
    @Override
    public Integer getHttpStatusCode(){
        return this.httpStatusCode;
    }

    @Override
    public Integer getErrorCode(){
        return this.errorCode;
    }

    @Override
    public String getDescription(){
        return this.description;
    }*/
}