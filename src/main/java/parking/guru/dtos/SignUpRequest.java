package parking.guru.dtos;

import lombok.Data;

@Data
public class SignUpRequest {

    private String username;

    private String password;

    private String name;

    private String email;
}
