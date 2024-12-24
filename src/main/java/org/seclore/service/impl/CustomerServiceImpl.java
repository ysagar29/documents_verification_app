package org.seclore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.seclore.enums.DocumentVerificationStatus;
import org.seclore.model.Customer;
import org.seclore.repository.CustomerRepo;
import org.seclore.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    @Override
    public Optional<Customer> submitDocuments(Customer customer, byte[] adhar, byte[] pancard) {
        try {
            customer.setAadharDocument(adhar);
            customer.setPanDocument(pancard);
            customer.setStatus(DocumentVerificationStatus.SUBMITTED);
            return Optional.of(customerRepo.save(customer));
        } catch (Exception ex) {
            log.error("Exception occurred at uploading: {}", ex.getMessage());
            throw new RuntimeException("Error in uploading documents");
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Optional<Customer> findByCustomerId(Long id) {
        return customerRepo.findById(id);
    }
}
