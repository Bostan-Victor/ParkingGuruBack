package parking.guru.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import parking.guru.models.Reservation;
import parking.guru.services.ReservationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationQueryResolverTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationQueryResolver reservationQueryResolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReservationHistory() {
        Long userId = 1L;

        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        reservations.add(reservation1);
        reservations.add(reservation2);

        when(reservationService.getReservationHistory(userId)).thenReturn(reservations);

        List<Reservation> result = reservationQueryResolver.reservationHistory(userId);

        assertEquals(2, result.size());
        verify(reservationService, times(1)).getReservationHistory(userId);
    }

    @Test
    void testActiveReservation() {
        Long userId = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(1L);

        when(reservationService.getActiveReservation(userId)).thenReturn(reservation);

        Reservation result = reservationQueryResolver.activeReservation(userId);

        assertEquals(1L, result.getId());
        verify(reservationService, times(1)).getActiveReservation(userId);
    }

    @Test
    void testReservationById() {
        Long reservationId = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);

        when(reservationService.getReservationById(reservationId)).thenReturn(Optional.of(reservation));

        Reservation result = reservationQueryResolver.reservationById(reservationId);

        assertEquals(reservationId, result.getId());
        verify(reservationService, times(1)).getReservationById(reservationId);
    }

    @Test
    void testAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        reservations.add(reservation1);
        reservations.add(reservation2);

        when(reservationService.getAllReservations()).thenReturn(reservations);

        List<Reservation> result = reservationQueryResolver.allReservations();

        assertEquals(2, result.size());
        verify(reservationService, times(1)).getAllReservations();
    }
}
