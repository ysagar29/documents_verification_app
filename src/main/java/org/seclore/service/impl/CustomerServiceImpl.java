package org.seclore.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.seclore.enums.DocumentVerificationStatus;
import org.seclore.model.Customer;
import org.seclore.repository.CustomerRepo;
import org.seclore.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public boolean submitDocuments(Customer customer, byte[] adhar, byte[] pancard) {
        try {
            customer.setAadharDocument(adhar);
            customer.setPanDocument(pancard);
            customer.setStatus(DocumentVerificationStatus.SUBMITTED);
            customerRepo.save(customer);
            return true;
        } catch (Exception ex) {
            log.error("Exception occurred at uploading: {}", ex.getMessage());
        }
        return false;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }
}
