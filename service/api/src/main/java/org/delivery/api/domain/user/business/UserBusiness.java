package org.delivery.api.domain.user.business;
// Controller가 Business에 요청보냄
// -> Business가 Service에 요청보냄

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.token.controller.model.TokenResponse;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Business
public class UserBusiness {

    // Controller에 사용자의 요청 들어옴 -> Controller가 Business에 요청보냄
    // -> Business가 Service에 요청보냄 -> Service가 DB Repository에 요청함

    private final UserService userService;

    private final UserConverter userConverter;

    private final TokenBusiness tokenBusiness;

    /**
     * 사용자에 대한 가입 처리 로직
     * 1. 해당 request를 Entity로 바꿔줌 (UserConverter)
     * 2. Entity가 저장된다.
     * 3. 저장된 Entity를 response로 변환
     * 4. response를 반환함.
    */
    public UserResponse register(UserRegisterRequest request) {

        var entity = userConverter.toEntity(request);
        var newEntity = userService.register(entity);
        var response = userConverter.toResponse(newEntity);
        return response;

        /*  위의 코드 람다식
        return Optional.ofNullable(request)
                .map(userConverter::toEntity)
                .map(userService::register)
                .map(userConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "request null"));
        */
    }

    /** 로그인 로직
     * 1. email, password 를 가지고 유효한 사용자인지 체크
     * 2. user entity 로그인 확인
     * 3. 로그인 확인 시 token 생성
     * 4. token response
     * */

    public TokenResponse login(UserLoginRequest request) {
        // email, password로 유효한 사용자인지 체크함.
        var userEntity = userService.login(request.getEmail(), request.getPassword());
        var tokenResponse = tokenBusiness.issueToken(userEntity);
        return tokenResponse;
    }


    public UserResponse me(User user){
        var userEntity = userService.getUserWithThrow(user.getId());
        var response = userConverter.toResponse(userEntity);
        return response;
    }
}
