package com.dev.nigrani.authentication.service;

import com.dev.nigrani.authentication.dtos.AuthResponse;
import com.dev.nigrani.authentication.dtos.LoginRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);
}