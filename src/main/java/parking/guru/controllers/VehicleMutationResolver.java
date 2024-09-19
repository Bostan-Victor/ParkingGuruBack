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
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber(input.getPlateNumber());
        vehicle.setType(Type.valueOf(input.getType()));

        vehicle.getUsers().add(user);
        user.getVehicles().add(vehicle);

        vehicleService.saveVehicle(vehicle);
        userService.saveUser(user);

        return vehicle;
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