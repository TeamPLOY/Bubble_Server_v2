package com.ploy.bubble_server_v3.domain.user.domain;

import com.ploy.bubble_server_v3.domain.user.domain.vo.Role;
import com.ploy.bubble_server_v3.domain.user.domain.vo.WashingRoom;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private Integer stuNum;

    @Column(nullable = false, unique = true)
    private String email;

    private String roomNum;

    @Enumerated(EnumType.STRING)
    private WashingRoom washingRoom;

    @Column(nullable = false)
    private Role role;

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateStuNum(Integer newStuNum) {
        this.stuNum = newStuNum;
    }

    public void updateRoomNum(String newRoomNum) {
        this.roomNum = newRoomNum;
    }

    public void updateWashingRoom(WashingRoom washingRoom) {
        this.washingRoom = washingRoom;
    }
}
