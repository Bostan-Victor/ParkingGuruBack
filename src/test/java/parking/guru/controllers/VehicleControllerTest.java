package parking.guru.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import parking.guru.models.Vehicle;
import parking.guru.services.VehicleService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static parking.guru.models.enums.Type.CAR;

class VehicleControllerTest {

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrUpdateVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber("ABC123");
        vehicle.setType(CAR);

        when(vehicleService.saveVehicle(any(Vehicle.class))).thenReturn(vehicle);

        ResponseEntity<Vehicle> response = vehicleController.createOrUpdateVehicle(vehicle);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ABC123", response.getBody().getPlateNumber());
        verify(vehicleService, times(1)).saveVehicle(any(Vehicle.class));
    }

    @Test
    void testGetVehicleById() {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber("XYZ789");

        when(vehicleService.getVehicleById(eq(1L))).thenReturn(Optional.of(vehicle));

        ResponseEntity<Vehicle> response = vehicleController.getVehicleById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("XYZ789", response.getBody().getPlateNumber());
        verify(vehicleService, times(1)).getVehicleById(eq(1L));
    }

    @Test
    void testGetVehicleById_NotFound() {
        when(vehicleService.getVehicleById(eq(1L))).thenReturn(Optional.empty());

        ResponseEntity<Vehicle> response = vehicleController.getVehicleById(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(vehicleService, times(1)).getVehicleById(eq(1L));
    }

    @Test
    void testGetAllVehicles() {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setPlateNumber("ABC123");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setPlateNumber("XYZ789");

        when(vehicleService.getAllVehicles()).thenReturn(Arrays.asList(vehicle1, vehicle2));

        ResponseEntity<List<Vehicle>> response = vehicleController.getAllVehicles();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(vehicleService, times(1)).getAllVehicles();
    }

    @Test
    void testGetAllVehicles_NoContent() {
        when(vehicleService.getAllVehicles()).thenReturn(Arrays.asList());

        ResponseEntity<List<Vehicle>> response = vehicleController.getAllVehicles();

        assertEquals(204, response.getStatusCodeValue());
        verify(vehicleService, times(1)).getAllVehicles();
    }

    @Test
    void testDeleteVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber("ABC123");

        when(vehicleService.getVehicleById(eq(1L))).thenReturn(Optional.of(vehicle));

        ResponseEntity<Void> response = vehicleController.deleteVehicle(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(vehicleService, times(1)).deleteVehicle(eq(1L));
    }

    @Test
    void testDeleteVehicle_NotFound() {
        when(vehicleService.getVehicleById(eq(1L))).thenReturn(Optional.empty());

        ResponseEntity<Void> response = vehicleController.deleteVehicle(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(vehicleService, times(0)).deleteVehicle(anyLong());
    }
}
