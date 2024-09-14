package parking.guru.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parking.guru.models.Reservation;
import parking.guru.repositories.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getReservationHistory(Long userId) {
        return reservationRepository.findByUserIdAndEndDateTimeIsNotNull(userId);
    }

    public Reservation getActiveReservation(Long userId) {
        return reservationRepository.findByUserIdAndEndDateTimeIsNull(userId);
    }

    @PreAuthorize("hasAuthority('POLICE')")
    public Reservation getReservationByPlateNumber(String plateNumber) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return reservationRepository.findByPlateNumber(plateNumber);
    }

    @Transactional
    public Reservation saveReservation(Reservation reservation) {
        // You can add custom validation logic here if necessary
        return reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public Optional<Reservation> getReservationByLocation(String latitude, String longitude) {
        return reservationRepository.findByLocation(latitude, longitude);
    }

    public Optional<Reservation> getReservationByStatus(parking.guru.models.enums.Status status) {
        return reservationRepository.findByStatus(status);
    }
}
