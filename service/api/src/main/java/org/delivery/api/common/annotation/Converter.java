package org.delivery.api.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) // 실행 중에 적용
@Service // spring에서 자동적으로 어노테이션 감지함 ...  Converter 어노테이션이 달린 클래스들은 데이터를 변환해준다.
public @interface Converter { // 데이터 변환 로직을 처리함. (DTO, Entity, 클래스 변환)

    @AliasFor(annotation = Service.class)
    String value() default "";
}
