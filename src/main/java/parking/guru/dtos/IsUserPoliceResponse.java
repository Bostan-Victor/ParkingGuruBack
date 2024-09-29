package parking.guru.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import parking.guru.models.Reservation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsUserPoliceResponse {
    private Boolean success; // True for POLICE
    private Reservation reservation; // Active reservation for USER, null if no reservation
}
