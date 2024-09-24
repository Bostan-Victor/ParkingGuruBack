package parking.guru.models;

import lombok.Data;

@Data
public class CreateUserInput {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String uid;
    private String role;
}
