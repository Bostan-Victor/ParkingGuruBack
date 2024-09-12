package parking.guru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import parking.guru.models.Vehicles;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicles, Long> {
    Optional<Vehicles> findByPlateNumber(String plateNumber);
}
