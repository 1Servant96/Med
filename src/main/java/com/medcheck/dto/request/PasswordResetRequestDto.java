package com.medcheck.dto.request;

import lombok.Data;

@Data
public class PasswordResetRequestDto {

    String phoneNumber;

    String firstName;

    String lastName;

    String oneTimePassword;

}
