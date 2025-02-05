package com.e_garden.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * L'interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find by email and enable optional.
     *
     * @param email  l'email
     * @param enable enable
     * @return l'optional
     */
    Optional<User> findByEmailAndEnable(String email, boolean enable);

    /**
     * Exists by email boolean.
     *
     * @param email l'email
     * @return le boolean
     */
    boolean existsByEmail(String email);

    /**
     * Find by identifiant and enable optional.
     *
     * @param id     l'identifiant
     * @param enable enable
     * @return l'optional
     */
    Optional<User> findByIdAndEnable(Long id, boolean enable);

    /**
     * Find all by enabled list.
     *
     * @param enable enable
     * @return le list
     */
    List<User> findAllByEnable(boolean enable);

    /**
     * Find all by enable and a role contains, or a role contains a list.
     *
     * @param enable enable
     * @param role   le role
     * @param role2  le role 2
     * @return le list
     */
    List<User> findAllByEnableAndRoleContainsOrRoleContains(boolean enable, String role, String role2);
}
