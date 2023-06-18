package com.medcheck.api;

import com.medcheck.db.service.TwilioOTPService;
import com.medcheck.dto.request.PasswordResetRequestDto;
import com.medcheck.dto.response.PasswordResetResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@CrossOrigin( origins = "*")
@Tag(name = "Twilio API", description = "Twilio API")
public class TwilioApi {

    private final TwilioOTPService twilioOTPService;

    @PostMapping("/router/sendOTP")
    public Mono<PasswordResetResponseDto> sendOTP(@RequestBody PasswordResetRequestDto passwordResetRequestDto) {
        return twilioOTPService.sendOTPForPasswordReset(passwordResetRequestDto);
    }

    @PostMapping("/router/validateOTP")
    public Mono<String> validateOTP(@RequestParam String userInputOtp, @RequestParam String firstName, @RequestParam String lastName) {
        return twilioOTPService.validateOtp(userInputOtp, firstName + " " + lastName);
    }

}
