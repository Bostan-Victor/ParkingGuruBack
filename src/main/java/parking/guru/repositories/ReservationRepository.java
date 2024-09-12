package parking.guru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import parking.guru.models.Reservations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import parking.guru.models.enums.Status;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservations, Long> {
    @Query("SELECT r FROM Reservations r WHERE r.latitude = :latitude AND r.longitude = :longitude")
    Optional<Reservations> findByLocation(@Param("latitude") String latitude, @Param("longitude") String longitude);

    @Query("SELECT r FROM Reservations r WHERE r.status = :status")
    Optional<Reservations> findByStatus(@Param("status") Status status);
}
