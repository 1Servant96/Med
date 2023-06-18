package com.medcheck.db.service;

import com.medcheck.dto.request.PasswordResetRequestDto;
import com.medcheck.dto.response.PasswordResetResponseDto;
import reactor.core.publisher.Mono;

public interface TwilioOTPService {

    Mono<PasswordResetResponseDto> sendOTPForPasswordReset(PasswordResetRequestDto passwordResetRequestDto);

    Mono<String> validateOtp(String userInputOtp, String userName);
}
