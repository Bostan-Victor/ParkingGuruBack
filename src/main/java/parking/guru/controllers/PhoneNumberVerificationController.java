package parking.guru.controllers;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parking.guru.dtos.OTPVerificationRequest;
import parking.guru.dtos.PhoneNumberRequest;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/phoneNumber")
@Slf4j
public class PhoneNumberVerificationController {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    private static final String PENDING = "pending";
    private static final String APPROVED = "approved";

    @GetMapping(value = "/generateOTP")
    public ResponseEntity<String> generateOTP(@RequestBody PhoneNumberRequest request){
        Twilio.init(accountSid, authToken);

        String phoneNumber = request.getPhoneNumber();

        Verification verification = Verification.creator(
                        "VA6872133421429a0b4c1573f655502d17",
                        phoneNumber,
                        "sms")
                .create();

        System.out.println(verification.getStatus());

        log.info("OTP has been successfully generated, and awaits your verification {}", LocalDateTime.now());

        return new ResponseEntity<>("Your OTP has been sent to your verified phone number", HttpStatus.OK);
    }

    @GetMapping("/verifyOTP")
    public ResponseEntity<String> verifyUserOTP(@RequestBody OTPVerificationRequest request) {
        Twilio.init(accountSid, authToken);

        try {
            String phoneNumber = request.getPhoneNumber();
            String code = request.getCode();

            VerificationCheck verificationCheck = VerificationCheck.creator(
                            "VA6872133421429a0b4c1573f655502d17")
                    .setTo(phoneNumber)
                    .setCode(code)
                    .create();
            if (verificationCheck.getStatus().equals(PENDING)) {
                return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
            } else {
                return ResponseEntity.of(Optional.of("This user's verification has been completed successfully"));
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
        }
    }
}
