package parking.guru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import parking.guru.models.Reservation;
import parking.guru.models.enums.Status;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByPlateNumber(String plateNumber);

    List<Reservation> findByUserIdAndEndDateTimeIsNotNull(Long userId);

    Reservation findByUserIdAndEndDateTimeIsNull(Long userId);

    // Update the query to use 'address' instead of 'latitude' and 'longitude'
    @Query("SELECT r FROM Reservation r WHERE r.address = :address")
    Optional<Reservation> findByAddress(@Param("address") String address);

    @Query("SELECT r FROM Reservation r WHERE r.status = :status")
    Optional<Reservation> findByStatus(@Param("status") Status status);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = :id AND r.endDateTime IS NULL ")
    Optional<Reservation> findActiveByUserId(Long id);

    @Query("SELECT r FROM Reservation r WHERE r.user.id = :id AND r.endDateTime IS NOT NULL ")
    List<Reservation> findAllByUserId(Long id);
}
