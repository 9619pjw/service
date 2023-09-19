package org.delivery.api.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


// 멀티 모듈의 경우 하위 패키지에 있는 어노테이션을 읽고 빈을 등록할 때 어려움이 발새
// => JPA 등록 위해 설정 추가함.
@Configuration
@EntityScan(basePackages = "org.delivery.db")
@EnableJpaRepositories(basePackages = "org.delivery.db")
public class JpaConfig {
}