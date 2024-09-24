package parking.guru.dtos;

import lombok.Data;

@Data
public class CreateReservationInput {
    private String address;
    private String plateNumber;
}
