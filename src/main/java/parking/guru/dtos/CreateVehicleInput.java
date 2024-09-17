package parking.guru.dtos;

import lombok.Data;

@Data
public class CreateVehicleInput {
    private String plateNumber;
    private String type;
}
