package parking.guru.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import parking.guru.models.Vehicles;
import parking.guru.repositories.VehicleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicles saveVehicle(Vehicles vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicles> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicles> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Optional<Vehicles> getVehicleByPlateNumber(String plateNumber) {
        return vehicleRepository.findByPlateNumber(plateNumber);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
