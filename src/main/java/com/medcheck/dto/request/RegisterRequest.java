package com.medcheck.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String password;

    private String firstName;

    private String lastName;

    private String phone;
}