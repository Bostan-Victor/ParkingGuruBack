package parking.guru.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parking.guru.models.Vehicles;
import parking.guru.services.VehicleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Create a new vehicle
    @PostMapping("/create")
    public ResponseEntity<Vehicles> createVehicle(@RequestBody Vehicles vehicle) {
        Vehicles newVehicle = vehicleService.saveVehicle(vehicle);
        return ResponseEntity.ok(newVehicle);
    }

    // Get all vehicles
    @GetMapping("/all")
    public ResponseEntity<List<Vehicles>> getAllVehicles() {
        List<Vehicles> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    // Get vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicles> getVehicleById(@PathVariable Long id) {
        Optional<Vehicles> vehicle = vehicleService.getVehicleById(id);
        if (vehicle.isPresent()) {
            return ResponseEntity.ok(vehicle.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get vehicle by plate number
    @GetMapping("/byPlate/{plateNumber}")
    public ResponseEntity<Vehicles> getVehicleByPlateNumber(@PathVariable String plateNumber) {
        Optional<Vehicles> vehicle = vehicleService.getVehicleByPlateNumber(plateNumber);
        if (vehicle.isPresent()) {
            return ResponseEntity.ok(vehicle.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a vehicle's information
    @PutMapping("/update/{id}")
    public ResponseEntity<Vehicles> updateVehicle(@PathVariable Long id, @RequestBody Vehicles updatedVehicle) {
        Optional<Vehicles> existingVehicle = vehicleService.getVehicleById(id);
        if (existingVehicle.isPresent()) {
            Vehicles vehicle = existingVehicle.get();
            vehicle.setPlateNumber(updatedVehicle.getPlateNumber());
            vehicle.setType(updatedVehicle.getType());
            // Save the updated vehicle
            Vehicles savedVehicle = vehicleService.saveVehicle(vehicle);
            return ResponseEntity.ok(savedVehicle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a vehicle
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        Optional<Vehicles> vehicle = vehicleService.getVehicleById(id);
        if (vehicle.isPresent()) {
            vehicleService.deleteVehicle(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
