package org.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.api.common.error.ErrorCodeIfs;

import jakarta.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    // @vaild로 유효성 검사함
    @Valid
    private T body;

    public static <T> Api<T> OK(T data){
        var api = new Api<T>();
        api.result = Result.OK();
        api.body = data;
        return api;
    }

    // 에러 ... 파라미터로 result 받음
    public static Api<Object> ERROR(Result result){
        var api = new Api<Object>();
        api.result = result;
        return api;
    }

    // 에러 ... 파라미터로 에러 코드를 받음
    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs);
        return api;
    }

    // 에러 ... 파라미터로 에러 코드와 최상위 예외를 받음
    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs, Throwable tx){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs, tx);
        return api;
    }

    // 에러 ... 파라미터로 에러 코드와 에러 메시지를 받음
    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs, String description){
        var api = new Api<Object>();
        api.result = Result.ERROR(errorCodeIfs, description);
        return api;
    }
}