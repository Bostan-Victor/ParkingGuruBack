package parking.guru.models;

import lombok.Data;
import parking.guru.models.enums.Status;

@Data
public class UpdateReservationInput {
    private String latitude;
    private String longitude;
    private String plateNumber;
    private Status status;
}
