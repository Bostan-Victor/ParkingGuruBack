package parking.guru.models;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import parking.guru.config.security.oauth2.OAuth2Provider;
import parking.guru.models.enums.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    // Added firstName and lastName fields
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    // UID Field
    @Column(nullable = false, unique = true)
    private String uid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(nullable = false)
    private Boolean isVerified = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;
}
