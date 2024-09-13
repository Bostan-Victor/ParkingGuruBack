package parking.guru.services;

import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import parking.guru.models.Reservation;
import parking.guru.repositories.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Fetch reservation history for a user (where endDateTime is not null)
    public List<Reservation> getReservationHistory(Long userId) {
        return reservationRepository.findByUserIdAndEndDateTimeIsNotNull(userId);
    }

    // Fetch active reservation for a user (where endDateTime is null)
    public Reservation getActiveReservation(Long userId) {
        return reservationRepository.findByUserIdAndEndDateTimeIsNull(userId);
    }

    @PreAuthorize("hasAuthority('POLICE')")
    // Fetch reservation by plate number
    public Reservation getReservationByPlateNumber(String plateNumber) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return reservationRepository.findByPlateNumber(plateNumber);
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}

