package org.delivery.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * token 에 대한 도메인로직
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    // TokenHelper 인터페이스 ... JWT 토큰, UUID 토큰 상황에 따라 바꿀 수 있음.
    private final TokenHelperIfs tokenHelperIfs;

    // token 발행 위해 userId 사용
    public TokenDto issueAccessToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    // 토큰 확인 후 로직에 사용할 userId 꺼내서 반환
    public Long validationToken(String token){
        var map = tokenHelperIfs.validationTokenWithThrow(token);

        var userId = map.get("userId");
        // userId null 예외 처리
        Objects.requireNonNull(userId, ()->{throw new ApiException(ErrorCode.NULL_POINT);});

        return Long.parseLong(userId.toString());
    }

}