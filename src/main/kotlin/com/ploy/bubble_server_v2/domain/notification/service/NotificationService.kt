package com.ploy.bubble_server_v2.domain.notification.service

import com.laundering.laundering_server.common.exception.BusinessException
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors

@Slf4j
@Service
@RequiredArgsConstructor
class NotificationService {
    @Autowired
    private val notificationRepository: NotificationRepository? = null

    @Autowired
    private val reservationRepository: ReservationRepository? = null

    @Autowired
    private val notifiReservationRepository: NotifiReservationRepository? = null

    @Autowired
    private val userRepository: UserRepository? = null

    val notification: List<Any>
        get() = notificationRepository.findAll().stream()
            .map { notification -> NotificationResponse(notification.getTitle(), notification.getDate()) }
            .collect(Collectors.toList<T>())
    val notificationDetail: List<Any>
        get() {
            return notificationRepository.findAll().stream()
                .map { notification ->
                    NotificationDetailResponse(
                        notification.getTitle(),
                        notification.getDetail(),
                        notification.getDate()
                    )
                }
                .collect(Collectors.toList<T>())
        }

    //    public List<ReservationLogResponse> getReservationHistory(Long id) {
    //        // 사용자 정보 조회
    //        User user = userRepository.findById(id)
    //                .orElseThrow(() -> new BusinessException(ErrorCode.UNKNOWN_ERROR));
    //
    //        // 해당 사용자의 예약 기록 조회
    //        List<ReservationLog> reservationLogs = reservationLogRepository.findByUserId(id);
    //
    //        // 예약 기록을 ReservationLogResponse로 변환하면서 washingRoom 추가
    //        return reservationLogs.stream()
    //                .map(log -> new ReservationLogResponse(
    //                        log.getDate().toLocalDate(),       // LocalDateTime에서 LocalDate로 변환
    //                        log.getResDate().toString(),       // LocalDate를 String으로 변환
    //                        log.isCancel(),                   // 취소 여부 전달
    //                        user.getWashingRoom()             // 사용자의 washingRoom 추가
    //                ))
    //                .collect(Collectors.toList());
    //    }
    fun getReservationHistory(id: Long?): List<ReservationLogResponse> {
        // 사용자 정보 조회
        val user: User = userRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.UNKNOWN_ERROR) }

        // 해당 사용자의 예약 기록 조회
        val reservation: List<Reservation> = reservationRepository.findByUserId(id)

        // 예약 기록을 ReservationLogResponse로 변환하면서 washingRoom 추가
        return reservation.stream()
            .map<Any> { log: Reservation ->
                ReservationLogResponse(
                    log.getDate(),  // LocalDate를 String으로 변환
                    log.isCancel(),  // 취소 여부 전달
                    user.getWashingRoom() // 사용자의 washingRoom 추가
                )
            }
            .collect(Collectors.toList<Any>())
    }

    fun saveNotification(request: saveNotificationRequest, userId: Long?) {
        // userId와 machine으로 NotifiReservation 조회
        val existingNotification: Optional<NotifiReservation> =
            notifiReservationRepository.findByUserIdAndMachine(userId, request.machine())

        // 기존 알림이 존재하면 삭제 후 함수 종료
        if (existingNotification.isPresent()) {
            notifiReservationRepository.delete(existingNotification.get())
            return  // 알림 삭제 후 함수 종료
        }


        // 새로운 NotifiReservation 엔티티 생성
        val notifiReservation: NotifiReservation = NotifiReservation.builder()
            .userId(userId)
            .machine(request.machine())
            .date(LocalDate.now()) // 현재 날짜로 설정
            .build()

        // DB에 저장
        notifiReservationRepository.save(notifiReservation)
    }


    fun getResNotification(request: saveNotificationRequest, userId: Long?): Boolean {
        // userId와 요청된 machine을 기준으로 NotifiReservation 조회
        val reservation: Optional<NotifiReservation> =
            notifiReservationRepository.findByUserIdAndMachine(userId, request.machine())

        // 예약 알림이 존재하면 true 반환, 존재하지 않으면 false 반환
        return reservation.isPresent()
    }
}