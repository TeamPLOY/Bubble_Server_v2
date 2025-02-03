package com.ploy.bubble_server_v3.domain.user.domain.repository;


import com.ploy.bubble_server_v3.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);

}
