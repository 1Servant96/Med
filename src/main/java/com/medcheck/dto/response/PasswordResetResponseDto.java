package com.medcheck.dto.response;

import com.medcheck.db.enums.OtpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponseDto {

    private OtpStatus status;

    private String message;
}
