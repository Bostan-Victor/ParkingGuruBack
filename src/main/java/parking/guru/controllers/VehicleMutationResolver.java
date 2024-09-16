package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import parking.guru.models.Vehicle;
import parking.guru.models.enums.Type;
import parking.guru.services.VehicleService;
import parking.guru.dtos.CreateVehicleInput;
import parking.guru.dtos.UpdateVehicleInput;

@Controller
@RequiredArgsConstructor
public class VehicleMutationResolver {

    private final VehicleService vehicleService;

    @MutationMapping
    public Vehicle createVehicle(@Argument CreateVehicleInput input) {
        // Create a new Vehicle object
        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber(input.getPlateNumber());
        vehicle.setType(Type.valueOf(input.getType()));

        return vehicleService.saveVehicle(vehicle);
    }

    @MutationMapping
    public Vehicle updateVehicle(@Argument Long id, @Argument UpdateVehicleInput input) {
        Vehicle vehicle = vehicleService.getVehicleById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (input.getPlateNumber() != null) {
            vehicle.setPlateNumber(input.getPlateNumber());
        }
        if (input.getType() != null) {
            vehicle.setType(Type.valueOf(input.getType()));
        }

        return vehicleService.saveVehicle(vehicle);
    }

    @MutationMapping
    public Boolean deleteVehicle(@Argument Long id) {
        Vehicle vehicle = vehicleService.getVehicleById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicleService.deleteVehicle(id);
        return true;
    }
}
