package parking.guru.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parking.guru.dtos.CreateReservationInput;
import parking.guru.dtos.UpdateReservationInput;
import parking.guru.models.Reservation;
import parking.guru.models.User;
import parking.guru.models.enums.Status;
import parking.guru.services.ReservationService;
import parking.guru.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    @Autowired
    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Reservation> createOrUpdateReservation(@RequestBody Reservation reservation) {
        Reservation savedReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/active/{userId}")
    public ResponseEntity<Reservation> getActiveReservation(@PathVariable Long userId) {
        Reservation activeReservation = reservationService.getActiveReservation(userId);
        if (activeReservation != null) {
            return ResponseEntity.ok(activeReservation);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Reservation>> getReservationHistory(@PathVariable Long userId) {
        List<Reservation> reservationHistory = reservationService.getReservationHistory(userId);
        if (reservationHistory.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservationHistory);
    }

    @GetMapping("/plate/{plateNumber}")
    public ResponseEntity<Reservation> getReservationByPlateNumber(@PathVariable String plateNumber) {
        Reservation reservation = reservationService.getReservationByPlateNumber(plateNumber);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/location")
    public ResponseEntity<Reservation> getReservationByLocation(@RequestParam String latitude, @RequestParam String longitude) {
        Optional<Reservation> reservation = reservationService.getReservationByLocation(latitude, longitude);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Reservation> getReservationByStatus(@PathVariable Status status) {
        Optional<Reservation> reservation = reservationService.getReservationByStatus(status);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/graphql/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationInput input) {
        User user = userService.findByPhoneNumber(input.getPhoneNumber());

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setLatitude(input.getLatitude());
        reservation.setLongitude(input.getLongitude());
        reservation.setPlateNumber(input.getPlateNumber());
        reservation.setStatus(Status.UNCHECKED);
        reservation.setStartDateTime(LocalDateTime.now());

        Reservation savedReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    @PutMapping("/graphql/update/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody UpdateReservationInput input) {
        Reservation reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (input.getLatitude() != null) {
            reservation.setLatitude(input.getLatitude());
        }
        if (input.getLongitude() != null) {
            reservation.setLongitude(input.getLongitude());
        }
        if (input.getEndDateTime() != null) {
            reservation.setEndDateTime(LocalDateTime.parse(input.getEndDateTime()));
        }
        if (input.getStatus() != null) {
            reservation.setStatus(input.getStatus());
        }

        Reservation updatedReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/graphql/delete/{id}")
    public ResponseEntity<Boolean> deleteReservationGraphQL(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservationService.deleteReservation(id);
        return ResponseEntity.ok(true);
    }
}
