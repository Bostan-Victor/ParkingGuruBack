package parking.guru.dtos;

import lombok.Data;

@Data
public class UpdateReservationInput {
    private String latitude;
    private String longitude;
    private String endDateTime;
    private parking.guru.models.enums.Status status;
}
