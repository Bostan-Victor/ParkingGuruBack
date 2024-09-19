package parking.guru.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import parking.guru.config.security.CustomUserDetails;
import parking.guru.models.User;
import parking.guru.models.Vehicle;
import parking.guru.models.enums.Type;
import parking.guru.services.UserService;
import parking.guru.services.VehicleService;
import parking.guru.dtos.CreateVehicleInput;
import parking.guru.dtos.UpdateVehicleInput;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class VehicleMutationResolver {

    private final VehicleService vehicleService;
    private final UserService userService;

    @MutationMapping
    public Vehicle createVehicle(@Argument CreateVehicleInput input) {
        CustomUserDetails customUserDetails =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = customUserDetails.getId();
        Optional<Vehicle> carEntity = userService.getUserById(userId).map(userEntity -> {
         Optional<Vehicle> vehicle =  vehicleService.getVehicle(input.getPlateNumber());
         if (vehicle.isPresent()) {
             userEntity.getVehicles().add(vehicle.get());
             userService.saveUser(userEntity);
             return vehicle.get();
         }
         Vehicle newVehicle = new Vehicle();
         newVehicle.setType(Type.valueOf(input.getType()));
         newVehicle.setPlateNumber(input.getPlateNumber());
         userEntity.getVehicles().add(newVehicle);
         return vehicleService.saveVehicle(newVehicle);
    });
        return carEntity.get();
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