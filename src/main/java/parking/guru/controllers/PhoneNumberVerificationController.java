package parking.guru.controllers;
import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import parking.guru.dtos.OTPVerificationRequest;
import parking.guru.dtos.PhoneNumberRequest;
import parking.guru.models.User;
import parking.guru.services.UserService;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "api/phoneNumber")
@Slf4j
public class PhoneNumberVerificationController {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    private static final String APPROVED = "approved";

    private final UserService userService;

    public PhoneNumberVerificationController(UserService userService) {
        this.userService = userService;
    }

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

            // Check the status of the verification
            if (APPROVED.equals(verificationCheck.getStatus())) {
                String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userService.validateAndGetUserByEmail(userEmail);

                user.setIsVerified(true);
                userService.saveUser(user);

                return new ResponseEntity<>("This user's verification has been completed successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Verification failed. Invalid OTP.", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
        }
    }
}
