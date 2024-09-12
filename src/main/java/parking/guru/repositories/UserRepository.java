package parking.guru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import parking.guru.models.Users;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByPhoneNumber(String phoneNumber);
}
