package parking.guru.models;

import lombok.Data;

@Data
public class CreateReservationInput {
    private String phoneNumber;
    private String latitude;
    private String longitude;
    private String plateNumber;
}
