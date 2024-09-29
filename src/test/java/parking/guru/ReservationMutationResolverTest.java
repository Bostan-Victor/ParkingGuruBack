package parking.guru;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import parking.guru.controllers.ReservationMutationResolver;
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
