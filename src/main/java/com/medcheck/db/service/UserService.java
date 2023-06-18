package com.medcheck.service;

import com.medcheck.dto.LoginResponse;
import com.medcheck.dto.request.UserRequest;

public interface UserService {

    LoginResponse login(UserRequest request);
}
