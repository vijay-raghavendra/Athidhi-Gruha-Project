package com.athidhi.auth_service.Repository;

import com.athidhi.auth_service.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByMobileNumber(String mobileNumber);

    Optional<User> findByDob(LocalDate dob);

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmailAndDob(String email,LocalDate dob);

    Optional<User> findByMobileNumberAndDob(String mobileNumber, LocalDate dob);

    Optional<User> findByMobileNumberAndEmail(String mobileNumber, String email);

    Optional<User> findUserIDByDobAndMobileNumberAndEmail(LocalDate dob,String mobileNumber,String email);

    Optional<User> findByUserIdAndDobAndMobileNumberAndEmail(
            String userId,
            LocalDate dob,
            String mobileNumber,
            String email
    );
}
