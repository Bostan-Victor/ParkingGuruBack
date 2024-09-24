package parking.guru.dtos;

public class OTPVerificationRequest {

    private String phoneNumber;
    private String code;

    // Constructors
    public OTPVerificationRequest() {}

    // Getters and setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

