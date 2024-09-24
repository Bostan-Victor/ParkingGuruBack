package parking.guru.dtos;

import lombok.Data;

@Data
public class CreateReservationInput {
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private String plateNumber;
}
