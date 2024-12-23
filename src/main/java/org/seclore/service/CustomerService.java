package org.seclore.service;

import org.seclore.model.Customer;

import java.util.List;

public interface CustomerService {

    boolean submitDocuments(Customer customer, byte[] adhar, byte[] pancard);

    List<Customer> getAllCustomers();

}
