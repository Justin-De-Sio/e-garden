package com.e_garden.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndEnable(String email, boolean enable);

    boolean existsByEmail(String email);

    Optional<User> findByIdAndEnable(Long id, boolean enable);

    List<User> findAllByEnable(boolean enable);
}
