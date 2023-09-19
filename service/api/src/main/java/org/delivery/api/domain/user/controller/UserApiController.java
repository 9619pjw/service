package org.delivery.api.domain.user.controller;
// Controller에 사용자의 요청 들어옴 -> Controller가 Business에 요청보냄

import lombok.RequiredArgsConstructor;

import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
//로그인을 해야만 사용 가능한 API
public class UserApiController {

    // 비즈니스 로직
    private final UserBusiness userBusiness;

    @GetMapping("/me")
    public Api<UserResponse> me(
            @UserSession User user
    ) {
        var response = userBusiness.me(null);
        return Api.OK(response);
    }
}
    /*
    public Api<UserResponse> me(){
     // requestContext ... request가 하나 들어올때마다 생성됨.
        // request는 Filter 통과 -> Interceptor 통과 -> Controller를 거치고 response 나갈 때까지 유지되는 global한 저장 영역 로컬 쓰레드
        var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());

        // Interceptor 에 의해 사용자 인증이 된 후 getAttribute로 값을 불러옴
        var userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);
        var response = userBusiness.me(Long.parseLong(userId.toString()));


        return Api.OK(response);*/


