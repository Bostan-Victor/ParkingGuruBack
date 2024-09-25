package parking.guru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import parking.guru.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
    @Query("SELECT u FROM User u WHERE u.email = :userEmail")
    Optional<User> findByUserEmail(@Param("userEmail") String userEmail);

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByUid(String uid);

    @Query("SELECT u FROM User u WHERE u.email = :username OR u.phoneNumber = :username")
    Optional<User> findByEmailOrPhoneNumber(@Param("username") String username);
}
