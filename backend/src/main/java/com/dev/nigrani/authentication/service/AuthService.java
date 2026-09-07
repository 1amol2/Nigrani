package com.dev.nigrani.authentication.service;

import com.dev.nigrani.authentication.models.User;

public interface AuthService {
    User register(User user);
    User login(User loginUser);
}
