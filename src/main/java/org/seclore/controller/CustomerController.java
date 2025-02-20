package org.seclore.controller;

import lombok.RequiredArgsConstructor;
import org.seclore.model.Customer;
import org.seclore.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<String> submitCustomer(
            @RequestPart @Valid Customer customer,
            @RequestPart MultipartFile adhar,
            @RequestPart MultipartFile pancard) throws IOException {
        if (adhar.isEmpty() || pancard.isEmpty()) {
            return ResponseEntity.badRequest().body("Files cannot be empty");
        }
        Optional<Customer> savedCustomer = customerService.submitDocuments(customer, adhar.getBytes(), pancard.getBytes());
        return savedCustomer.map(value -> ResponseEntity.status(HttpStatus.CREATED)
                        .body("Customer details submitted successfully with reference id: " + value.getId()))
                .orElseGet(() -> ResponseEntity.internalServerError().body("Uploading failed"));
    }

    @GetMapping("/status")
    public ResponseEntity<?> findByCustomerId(@RequestParam Long id) {
        Optional<Customer> customer = customerService.findByCustomerId(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }
        return ResponseEntity.badRequest().body("Customer not found for id: " + id);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

}
