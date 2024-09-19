package parking.guru.dtos;

import lombok.Data;

@Data
public class UpdateVehicleInput {
    private String plateNumber;
    private String type;
}
