package com.ploy.bubble_server_v3.domain.reservation.presentation;

import com.ploy.bubble_server_v3.domain.reservation.presentation.dto.request.CreateReservationRequest;
import com.ploy.bubble_server_v3.domain.reservation.presentation.dto.request.UpdateReservationRequest;
import com.ploy.bubble_server_v3.domain.reservation.presentation.dto.response.ReservationResponse;
import com.ploy.bubble_server_v3.domain.reservation.service.CommandReservationService;
import com.ploy.bubble_server_v3.domain.reservation.service.QueryReservationService;
import com.ploy.bubble_server_v3.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    private final CommandReservationService commandReservationService;
    private final QueryReservationService queryReservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateReservationRequest createReservationRequest,
                       @AuthenticationPrincipal Users user) {
        commandReservationService.create(createReservationRequest.toEntity(), user);
    }

    @GetMapping("/{reservation-id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationResponse readOne(@PathVariable("reservation-id") Long reservationId) {
        return ReservationResponse.from(queryReservationService.readOne(reservationId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> readAll() {
        return queryReservationService.readAll().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @GetMapping("/mine")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> readMine(@AuthenticationPrincipal Users user) {
        return queryReservationService.readByUser(user).stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @GetMapping("/week")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationResponse> readWeek() {
        return queryReservationService.findByWeek().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    @PutMapping("/{reservation-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UpdateReservationRequest updateReservationRequest,
                       @PathVariable("reservation-id") Long reservationId,
                       @AuthenticationPrincipal Users user) {
        commandReservationService.update(reservationId, updateReservationRequest.toEntity(), user);
    }

    @PatchMapping("/{reservation-id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable("reservation-id") Long reservationId,
                       @AuthenticationPrincipal Users user) {
        commandReservationService.cancel(reservationId, user);
    }
}
