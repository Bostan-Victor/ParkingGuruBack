package parking.guru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import parking.guru.models.Profiles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profiles, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Profiles p WHERE p.id = :id AND p.isVerified = TRUE")
    Optional<Boolean> isUserVerified(@Param("id") Long id);
}

