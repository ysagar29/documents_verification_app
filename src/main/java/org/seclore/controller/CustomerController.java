package org.seclore.controller;

import jakarta.validation.Valid;
import org.seclore.model.Customer;
import org.seclore.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<String> submitCustomer(
            @RequestPart @Valid Customer customer,
            @RequestPart MultipartFile adhar,
            @RequestPart MultipartFile pancard) throws IOException {
        if (adhar.isEmpty() || pancard.isEmpty()) {
            return ResponseEntity.badRequest().body("Files cannot be empty");
        }
        if (customerService.submitDocuments(customer, adhar.getBytes(), pancard.getBytes())) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer details submitted successfully!");
        }
        return ResponseEntity.internalServerError().body("Uploading failed");
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

}
