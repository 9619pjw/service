package org.delivery.api.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) // 실행 중에 적용
@Service // spring에서 자동적으로 어노테이션 감지함 ...  Business 어노테이션이 달린 클래스들도 빈으로 자동 등록된다.
public @interface Business { // 비즈니스 로직을 처리하는 역할

    @AliasFor(annotation = Service.class)
    String value() default "";
}
