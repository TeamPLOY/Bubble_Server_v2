package com.ploy.bubble_server_v2.domain.user.service

import com.ploy.bubble_server_v2.common.exception.BusinessException
import com.ploy.bubble_server_v2.common.exception.ErrorCode
import com.ploy.bubble_server_v2.domain.user.model.dto.request.SignUpRequest
import com.ploy.bubble_server_v2.domain.user.model.dto.response.UserResponse
import com.ploy.bubble_server_v2.domain.user.model.dto.vo.WashingRoom
import com.ploy.bubble_server_v2.domain.user.model.entity.User
import com.ploy.bubble_server_v2.domain.user.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
class UserService {
    private val userRepository: UserRepository? = null

    @org.springframework.transaction.annotation.Transactional
    fun create(req: SignUpRequest) {
        try {
            // 이메일 중복 여부 확인

            if (userRepository.existsByEmail(req.email())) {
                throw BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS)
            }

            // roomNum에서 prefix ("A", "B")와 숫자 추출
            val prefix: String = req.roomNum().substring(0, 1)
            val roomNumber: Int = req.roomNum().substring(1).toInt()

            // Enum을 이용해 washingRoom 값 결정
            val washingRoom: String = WashingRoom.findWashingRoom(prefix, roomNumber)

            userRepository.save(
                User.builder()
                    .email(req.email()) // 사용자 아이디
                    .password(BCryptPasswordEncoder().encode(req.password())) // 비밀번호
                    .name(req.name())
                    .stuNum(req.stuNum()) // 아이 아이디
                    .roomNum(req.roomNum()) // roomNum 저장
                    .washingRoom(washingRoom) // 계산된 washingRoom 저장
                    .build()
            )
        } catch (e: java.lang.Exception) {
            UserService.log.error("회원 생성 중 오류 발생: {}", e.message)
            throw BusinessException(ErrorCode.UNKNOWN_ERROR)
        }
    }

    fun getUserInfo(memberId: Long?): UserResponse {
        val user: User? = userRepository!!.findById(memberId)
            .orElseThrow { BusinessException(ErrorCode.UNKNOWN_ERROR) }

        return UserResponse.builder()
            .name(user.getName())
            .studentNum(user.getStuNum())
            .email(user.getEmail())
            .roomNum(user.getRoomNum())
            .washingRoom(user.getWashingRoom())
            .build()
    }

    fun deleteUser(memberId: Long?) {
        val userOptional: java.util.Optional<User> = userRepository.findById(memberId)

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get())
        } else {
            throw BusinessException(ErrorCode.ENTITY_NOT_FOUND)
        }
    }
}