package parking.guru.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parking.guru.models.Reservation;
import parking.guru.repositories.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationHistory(Long userId) {
        return reservationRepository.findByUserIdAndEndDateTimeIsNotNull(userId);
    }

    public Reservation getActiveReservation(Long userId) {
        return reservationRepository.findByUserIdAndEndDateTimeIsNull(userId);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
