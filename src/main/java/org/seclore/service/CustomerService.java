package org.seclore.service;

import org.seclore.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> submitDocuments(Customer customer, byte[] adhar, byte[] pancard);

    List<Customer> getAllCustomers();

    Optional<Customer> findByCustomerId(Long id);

}
