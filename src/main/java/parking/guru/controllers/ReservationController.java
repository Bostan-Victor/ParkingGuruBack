package parking.guru.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import parking.guru.models.Reservation;
import parking.guru.models.enums.Status;
import parking.guru.services.ReservationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Create or update a reservation
    @PostMapping
    public ResponseEntity<Reservation> createOrUpdateReservation(@RequestBody Reservation reservation) {
        Reservation savedReservation = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    // Get reservation by ID (fixed method)
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get active reservation for a user by user ID
    @GetMapping("/active/{userId}")
    public ResponseEntity<Reservation> getActiveReservation(@PathVariable Long userId) {
        Reservation activeReservation = reservationService.getActiveReservation(userId);
        if (activeReservation != null) {
            return ResponseEntity.ok(activeReservation);
        }
        return ResponseEntity.notFound().build();
    }

    // Get reservation history for a user
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Reservation>> getReservationHistory(@PathVariable Long userId) {
        List<Reservation> reservationHistory = reservationService.getReservationHistory(userId);
        if (reservationHistory.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservationHistory);
    }

    // Get reservation by plate number
    @GetMapping("/plate/{plateNumber}")
    public ResponseEntity<Reservation> getReservationByPlateNumber(@PathVariable String plateNumber) {
        Reservation reservation = reservationService.getReservationByPlateNumber(plateNumber);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
        }
        return ResponseEntity.notFound().build();
    }

    // Get reservation by location (latitude and longitude)
    @GetMapping("/location")
    public ResponseEntity<Reservation> getReservationByLocation(@RequestParam String latitude, @RequestParam String longitude) {
        Optional<Reservation> reservation = reservationService.getReservationByLocation(latitude, longitude);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get reservation by status
    @GetMapping("/status/{status}")
    public ResponseEntity<Reservation> getReservationByStatus(@PathVariable Status status) {
        Optional<Reservation> reservation = reservationService.getReservationByStatus(status);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    // Delete a reservation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
