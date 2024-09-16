package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.Vehicle;
import parking.guru.services.VehicleService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VehicleQueryResolver {

    private final VehicleService vehicleService;

    @QueryMapping
    public List<Vehicle> allVehicles() {
        return vehicleService.getAllVehicles();
    }
    @QueryMapping
    public Vehicle vehicleById(@Argument Long id) {
        return vehicleService.getVehicleById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
    }
    @QueryMapping
    public Vehicle vehicleByPlateNumber(@Argument String plateNumber) {
        return vehicleService.getVehicleByPlateNumber(plateNumber)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with plate number: " + plateNumber));
    }
}
