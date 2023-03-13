package com.example.apiv2.service;

import com.example.apiv2.model.UserDtls;

public interface UserService {

    public UserDtls createUser(UserDtls user);

    public boolean checkEmail(String email);
}
