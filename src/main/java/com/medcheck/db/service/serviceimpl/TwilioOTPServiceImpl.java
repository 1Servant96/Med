package com.medcheck.db.service.serviceimpl;

import com.medcheck.config.twilio.TwilioConfig;
import com.medcheck.db.enums.OtpStatus;
import com.medcheck.db.service.TwilioOTPService;
import com.medcheck.dto.request.PasswordResetRequestDto;
import com.medcheck.dto.response.PasswordResetResponseDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TwilioOTPServiceImpl implements TwilioOTPService {

    @Autowired
    private TwilioConfig twilioConfig;

    Map<String, String> otpMap = new HashMap<>();

    @Override
    public Mono<PasswordResetResponseDto> sendOTPForPasswordReset(PasswordResetRequestDto passwordResetRequestDto) {
        PasswordResetResponseDto passwordResetResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String otp = generateOTP();
            String otpMessage = "Dear customer, Your OTP is ##" + otp + "##. Use this Passcode to complete your transaction. Thank you.";
            Message message = Message.creator(to, from,
                    otpMessage).create();
            otpMap.put(passwordResetRequestDto.getFirstName() + " " + passwordResetRequestDto.getLastName(), otp);
            passwordResetResponseDto = new PasswordResetResponseDto(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception ex) {
            passwordResetResponseDto = new PasswordResetResponseDto(OtpStatus.FAILED, ex.getMessage());
        }
        return Mono.just(passwordResetResponseDto);
    }

    @Override
    public Mono<String> validateOtp(String userInputOtp, String userName) {
        if (userInputOtp.equals(otpMap.get(userName))) {
            otpMap.remove(userName, userInputOtp);
            return Mono.just("Valid otp please proceed with your transaction!");
        } else {
            return Mono.error(new IllegalArgumentException("Invalid otp please retry!"));
        }
    }

    private String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
