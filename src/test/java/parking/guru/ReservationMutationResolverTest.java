package parking.guru.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import parking.guru.dtos.CreateReservationInput;
import parking.guru.dtos.UpdateReservationInput;
import parking.guru.models.Reservation;
import parking.guru.models.User;
import parking.guru.models.enums.Status;
import parking.guru.services.ReservationService;
import parking.guru.services.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationMutationResolverTest {

    @Mock
    private ReservationService reservationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReservationMutationResolver reservationMutationResolver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReservation() {
        CreateReservationInput input = new CreateReservationInput();
        input.setPhoneNumber("1234567890");
        input.setLatitude("51.5074");
        input.setLongitude("-0.1278");
        input.setPlateNumber("ABC123");

        User user = new User();
        user.setPhoneNumber("1234567890");

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setLatitude("51.5074");
        reservation.setLongitude("-0.1278");
        reservation.setPlateNumber("ABC123");
        reservation.setStatus(Status.UNCHECKED);
        reservation.setStartDateTime(LocalDateTime.now());

        when(userService.findByPhoneNumber(any())).thenReturn(user);
        when(reservationService.saveReservation(any(Reservation.class))).thenReturn(reservation);

        Reservation createdReservation = reservationMutationResolver.createReservation(input);

        assertEquals("ABC123", createdReservation.getPlateNumber());
        verify(reservationService, times(1)).saveReservation(any(Reservation.class));
    }

    @Test
    void testUpdateReservation() {
        Long reservationId = 1L;
        UpdateReservationInput input = new UpdateReservationInput();
        input.setLatitude("48.8566");
        input.setLongitude("2.3522");
        input.setStatus(Status.CHECKED);

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setLatitude("51.5074");
        reservation.setLongitude("-0.1278");

        when(reservationService.getReservationById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationService.saveReservation(any(Reservation.class))).thenReturn(reservation);

        Reservation updatedReservation = reservationMutationResolver.updateReservation(reservationId, input);

        assertEquals("48.8566", updatedReservation.getLatitude());
        assertEquals(Status.CHECKED, updatedReservation.getStatus());
        verify(reservationService, times(1)).saveReservation(any(Reservation.class));
    }

    @Test
    void testDeleteReservation() {
        Long reservationId = 1L;

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);

        when(reservationService.getReservationById(reservationId)).thenReturn(Optional.of(reservation));

        Boolean result = reservationMutationResolver.deleteReservation(reservationId);

        assertEquals(true, result);
        verify(reservationService, times(1)).deleteReservation(reservationId);
    }
}
