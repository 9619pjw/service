package org.delivery.api.domain.user.controller;
// Controller에 사용자의 요청 들어옴 -> Controller가 Business에 요청보냄

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/user")
// 공개된 API
public class UserOpenApiController {

    // 비즈니스 로직
    private final UserBusiness userBusiness;


    // 사용자 가입 요청
    @PostMapping("/register")
    public Api<UserResponse> register(
            @Valid // ... @NotBlank, @Email 동작시켜 검증함.
            @RequestBody Api<UserRegisterRequest> request // 사용자 가입에 대한 데이터 들어옴
    ){
        var response = userBusiness.register(request.getBody()); // 비즈니스 로직 처리
        return Api.OK(response);
    }

    // 로그인
    @PostMapping("/login")
    public Api<TokenResponse> login(
            @Valid
            @RequestBody Api<UserLoginRequest> request
    ){
        var response = userBusiness.login(request.getBody());
        return Api.OK(response);
    }


}
