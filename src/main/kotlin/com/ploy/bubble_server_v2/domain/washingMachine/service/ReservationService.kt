package com.ploy.bubble_server_v2.domain.washingMachine.service

import com.laundering.laundering_server.common.exception.BusinessException

@lombok.extern.slf4j.Slf4j
@org.springframework.stereotype.Service
@RequiredArgsConstructor
class ReservationService {
    @Autowired
    private val reservationLogRepository: ReservationLogRepository? = null

    @Autowired
    private val reservationRepository: ReservationRepository? = null

    @Autowired
    private val userRepository: UserRepository? = null

    //    public void reservation(Long userId, LocalDate date) {
    //        User user = userRepository.findById(userId)
    //                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
    //
    //        // userId와 오늘 날짜로 예약 조회
    //        Optional<Reservation> existingReservation = reservationRepository.findByUserIdAndDate(userId, date);
    //
    //        // 예약이 이미 존재하면 처리
    //        if (existingReservation.isPresent()) {
    //            throw new BusinessException(ErrorCode.RESERVATION_ALREADY_EXISTS);
    //        }
    //
    //        // 주 시작일과 끝일 계산 (예: 일요일 시작, 토요일 끝)
    //        LocalDate startOfWeek = date.with(DayOfWeek.SUNDAY);
    //        LocalDate endOfWeek = date.with(DayOfWeek.SATURDAY);
    //
    //        // 해당 주에 예약이 있는지 확인
    //        List<Reservation> weeklyReservations = reservationRepository.findByUserIdAndDateBetween(userId, startOfWeek, endOfWeek);
    //
    //        if (!weeklyReservations.isEmpty()) {
    //            throw new BusinessException(ErrorCode.RESERVATION_ALREADY_EXISTS_THIS_WEEK);
    //        }
    //
    //        // 새로운 예약 생성
    //        Reservation reservation = Reservation.builder()
    //                .userId(userId)        // 예약한 사용자 ID
    //                .date(date)           // 예약 날짜 (현재 날짜)
    //                .washingRoom(user.getWashingRoom()) // User 테이블에서 조회한 washingRoom 값 설정
    //                .build();
    //
    //        ReservationLog reservationLog = ReservationLog.builder()
    //                .userId(userId)
    //                .isCancel(false)
    //                .date(LocalDateTime.now())
    //                .resDate(date)
    //                .washingRoom(user.getWashingRoom())
    //                .build();
    //
    //        // 예약을 데이터베이스에 저장
    //        reservationRepository.save(reservation);
    //        reservationLogRepository.save(reservationLog);
    //    }
    fun reservation(userId: Long?, date: java.time.LocalDate) {
        val user: User = userRepository.findById(userId)
            .orElseThrow { BusinessException(ErrorCode.ENTITY_NOT_FOUND) }

        val existingReservation: java.util.Optional<Reservation> =
            reservationRepository.findByUserIdAndDateAndIsCancelFalse(userId, date)

        if (existingReservation.isPresent()) {
            throw BusinessException(ErrorCode.RESERVATION_ALREADY_EXISTS)
        }

        // 주 시작일과 끝일 계산 (예: 일요일 시작, 토요일 끝)
        val startOfWeek = date.with(DayOfWeek.SUNDAY)
        val endOfWeek = date.with(DayOfWeek.SATURDAY)

        // 해당 주에 예약이 있는지 확인
        val weeklyReservations: List<Reservation> =
            reservationRepository.findByUserIdAndDateBetween(userId, startOfWeek, endOfWeek)

        if (!weeklyReservations.isEmpty()) {
            throw BusinessException(ErrorCode.RESERVATION_ALREADY_EXISTS_THIS_WEEK)
        }

        // 새로운 예약 생성
        val reservation: Reservation = Reservation.builder()
            .userId(userId) // 예약한 사용자 ID
            .date(date) // 예약 날짜 (현재 날짜)
            .isCancel(false)
            .washingRoom(user.getWashingRoom()) // User 테이블에서 조회한 washingRoom 값 설정
            .build()

        //        ReservationLog reservationLog = ReservationLog.builder()
//                .userId(userId)
//                .isCancel(false)
//                .date(LocalDateTime.now())
//                .resDate(date)
//                .washingRoom(user.getWashingRoom())
//                .build();

        // 예약을 데이터베이스에 저장
        reservationRepository.save(reservation)
        //        reservationLogRepository.save(reservationLog);
    }


    //    public void cancelReservation(Long userId, LocalDate date) {
    //        User user = userRepository.findById(userId)
    //                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
    //
    //        Optional<Reservation> reservationOpt = reservationRepository.findByUserIdAndDate(userId, date);
    //
    //        // 예약이 존재하면 isCancel을 true로 변경하고 저장
    //        if (reservationOpt.isPresent()) {
    //            Reservation reservation = reservationOpt.get();
    //            // 새로운 예약 생성
    //            ReservationLog reservationLog = ReservationLog.builder()
    //                    .userId(userId)
    //                    .isCancel(true)
    //                    .date(LocalDateTime.now())
    //                    .resDate(date)
    //                    .washingRoom(user.getWashingRoom())
    //                    .build();
    //
    //            reservationRepository.delete(reservation); // 예약 삭제
    //            reservationLogRepository.save(reservationLog);
    //
    //        } else {
    //            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
    //        }
    //    }
    fun cancelReservation(userId: Long?, date: java.time.LocalDate?) {
        // userId와 date, isCancel이 false인 예약 조회
        val reservation: Reservation = reservationRepository.findByUserIdAndDateAndIsCancelFalse(userId, date)
            .orElseThrow { BusinessException(ErrorCode.ENTITY_NOT_FOUND) }

        // isCancel을 true로 업데이트
        reservation.setCancel(true)

        // 변경사항을 데이터베이스에 저장
        reservationRepository.save(reservation)
    }


    fun getReservation(userId: Long?): List<ReservationSummaryResponse> {
        val user: User = userRepository.findById(userId)
            .orElseThrow { BusinessException(ErrorCode.ENTITY_NOT_FOUND) }

        val washingRoom: String = user.getWashingRoom()

        val now = java.time.LocalDateTime.now()
        val today: DayOfWeek = now.dayOfWeek

        val startTime: java.time.LocalDate
        val endTime: java.time.LocalDate

        // 현재 시간이 일요일 22:00~23:59 사이인지 확인
        if (today == DayOfWeek.SUNDAY && now.hour >= 22) {
            // 다음 주 월요일부터 목요일까지 설정
            startTime = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).toLocalDate()
            endTime = now.with(TemporalAdjusters.next(DayOfWeek.THURSDAY)).toLocalDate()
        } else {
            // 이번 주 월요일부터 목요일까지 설정
            startTime = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).toLocalDate()
            endTime = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY)).toLocalDate()
        }

        // 예약 기록 조회
        val reservations: List<Reservation> =
            reservationRepository.findByWashingRoomAndDateBetween(washingRoom, startTime, endTime)

        // 월요일부터 목요일에 해당하는 날짜만 필터링
        val allWeekdays = startTime.datesUntil(endTime.plusDays(1))
            .filter { date: java.time.LocalDate ->
                val dayOfWeek: DayOfWeek = date.dayOfWeek
                dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.THURSDAY
            }
            .collect(java.util.stream.Collectors.toList<java.time.LocalDate>())

        // 날짜별 유저 수 매핑
        val reservationCountMap: Map<java.time.LocalDate, Long> = reservations.stream()
            .collect(
                java.util.stream.Collectors.groupingBy<Any, Any, Any, Long>(
                    Reservation::getDate,
                    java.util.stream.Collectors.counting<Any>()
                )
            )

        // 월요일부터 목요일에 대해서만 userCount를 0으로 설정, 예약이 있으면 해당 유저 수로 덮어쓰기
        val summaryList: List<ReservationSummaryResponse> = allWeekdays.stream()
            .map<Any> { date: java.time.LocalDate ->
                ReservationSummaryResponse(
                    date,
                    date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN),
                    reservationCountMap.getOrDefault(date, 0L)
                )
            }
            .collect(java.util.stream.Collectors.toList<Any>())

        return summaryList
    }


    fun getIsReserved(userId: Long?): Boolean {
        // 현재 시간 가져오기
        val now = java.time.LocalDateTime.now()
        val dayOfWeek: DayOfWeek = now.dayOfWeek

        // 현재 요일이 일요일이고 시간대가 22:00 ~ 23:59인지 확인
        val isSundayLateEvening = dayOfWeek == DayOfWeek.SUNDAY &&
                now.toLocalTime().isAfter(java.time.LocalTime.of(22, 0)) &&
                now.toLocalTime().isBefore(java.time.LocalTime.of(23, 59, 59))

        // 기간 설정을 위한 날짜 계산
        val startDate: java.time.LocalDate
        val endDate: java.time.LocalDate

        if (isSundayLateEvening) {
            // 이번 주 일요일 22:00부터 다음 주 일요일 21:59까지
            startDate = now.toLocalDate()
            endDate = now.plusWeeks(1).with(DayOfWeek.SUNDAY).minusHours(2).toLocalDate()
        } else {
            // 저번 주 일요일 22:00부터 이번 주 일요일 21:59까지
            startDate = now.minusWeeks(1).with(DayOfWeek.SUNDAY).toLocalDate()
            endDate = now.with(DayOfWeek.SUNDAY).minusHours(2).toLocalDate()
        }

        // 해당 기간 동안 userId로 예약된 내역이 있는지 조회
        val reservations: List<Reservation> =
            reservationRepository.findByUserIdAndDateBetween(userId, startDate, endDate)

        // 예약 내역이 존재하면 true 반환
        return !reservations.isEmpty()
    }
}