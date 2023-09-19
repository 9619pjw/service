package org.delivery.api.domain.user.service;
// Business가 Service에 요청보냄 -> Service가 DB Repository에 요청함

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * User 도메인 로직을 처리하는 서비스
 * Entity 등록
 * */


@RequiredArgsConstructor
@Service
public class UserService {

    // 자신의 도메인 로직만 처리
    private final UserRepository userRepository;

    // Entity 등록
    public UserEntity register(UserEntity userEntity){

        return Optional.ofNullable(userEntity)
                .map(it ->{
                    userEntity.setStatus(UserStatus.REGISTERED);
                    userEntity.setRegisteredAt(LocalDateTime.now());
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity Null"));

    }

    public UserEntity login(String email, String password){

        var entity = getUserWithThrow(email, password);
        return entity;
    }


    public UserEntity getUserWithThrow(String email, String password){
        // 해당 email과 password로 "등록된 사용자"가 있는지 확인함. 없는 경우 "사용자를 찾을 수 없음" 발생함
        return userRepository.findFirstByEmailAndPasswordAndStatusOrderByIdDesc(
                email, password, UserStatus.REGISTERED
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
    // overloading
    public UserEntity getUserWithThrow(Long userId){
        // 해당 userId로 "등록된 사용자"가 있는지 확인함. 없는 경우 "사용자를 찾을 수 없음" 발생함
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(
                userId, UserStatus.REGISTERED
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }
}
