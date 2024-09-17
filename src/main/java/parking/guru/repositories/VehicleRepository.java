package parking.guru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import parking.guru.models.Vehicle;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByPlateNumber(String plateNumber);
}
