package org.delivery.api.common.error;

// Enum 클래스는 상속이 불가능함 ... 인터페이스를 생성
public interface ErrorCodeIfs {

    Integer getHttpStatusCode();

    Integer getErrorCode();

    String getDescription();
}