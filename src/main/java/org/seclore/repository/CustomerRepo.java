package org.seclore.repository;

import org.seclore.enums.DocumentVerificationStatus;
import org.seclore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.status = :status")
    List<Customer> findAllByStatus(@Param("status") DocumentVerificationStatus status);

    Optional<Customer> findById(Long id);
}
