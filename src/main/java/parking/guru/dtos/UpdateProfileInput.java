package parking.guru.dtos;

import lombok.Data;

@Data
public class UpdateProfileInput {
    private String firstName;
    private String lastName;
    private Boolean isVerified;
}
