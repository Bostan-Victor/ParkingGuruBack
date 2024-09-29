package parking.guru.dtos;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String phoneNumber;
    private String password;
    private String uuid;
    private String firstName;
    private String lastName;
}
