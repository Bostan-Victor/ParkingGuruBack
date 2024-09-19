package parking.guru.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import parking.guru.models.Reservation;
import parking.guru.models.enums.Status;
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

    public Reservation getReservationByPlateNumber(String plateNumber) {
        return reservationRepository.findByPlateNumber(plateNumber);
    }

    public Optional<Reservation> getReservationByLocation(String latitude, String longitude) {
        return reservationRepository.findByLocation(latitude, longitude);
    }

    public Optional<Reservation> getReservationByStatus(Status status) {
        return reservationRepository.findByStatus(status);
    }

    public List<Reservation> getReservationHistory(Long userId) {
        return reservationRepository.findByUserIdAndEndDateTimeIsNotNull(userId);
    }

    public Reservation getActiveReservation(Long userId) {
        return reservationRepository.findByUserIdAndEndDateTimeIsNull(userId);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getAllReservationsByUserId(Long userId) {
        return reservationRepository.findAllByUserId(userId);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Optional<Reservation> getReservationByUserId(Long id) {
        return reservationRepository.findActiveByUserId(id);
    }
}