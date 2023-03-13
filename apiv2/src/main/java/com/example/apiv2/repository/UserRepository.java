package com.example.apiv2.repository;

import com.example.apiv2.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    public boolean existsByEmail(String email);

    public UserDtls findByEmail(String email);

    public UserDtls findByEmailAndMobileNumber(String email, String mobileNum);
}
