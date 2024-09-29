package parking.guru.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import parking.guru.models.Reservation;
import parking.guru.models.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private Boolean success;
    private String message;
    private Reservation reservation;
    private User user;
}
