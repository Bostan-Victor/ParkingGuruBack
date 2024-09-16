package parking.guru.dtos;

import lombok.Data;

@Data
public class UpdateUserInput {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String role;
}
