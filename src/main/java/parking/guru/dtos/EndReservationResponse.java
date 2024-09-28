package parking.guru.dtos;

public class EndReservationResponse {
    private Long id;
    private String plateNumber;
    private String startDateTime;
    private String endDateTime;
    private double totalPrice;

    // Constructor
    public EndReservationResponse(Long id, String plateNumber, String startDateTime, String endDateTime, double totalPrice) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
