package com.ploy.bubble_server_v3.domain.reservation.domain;

import com.ploy.bubble_server_v3.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private Boolean cancel;

    @CreationTimestamp
    private LocalDateTime date;

    private LocalDateTime resDate;

    private Integer unit;

    public void updateUser(Users user) {
        this.user = user;
    }

    public void cancel() {
        this.cancel = true;
    }

    public void update(Reservation reservation) {
        this.resDate = reservation.resDate;
        this.unit = reservation.unit;
    }
}
