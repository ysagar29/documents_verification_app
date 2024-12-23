package org.seclore.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.seclore.model.Customer;
import org.seclore.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public String home() {
        return "Current date is " + LocalDate.now();
    }

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<String> submitCustomer(
            @RequestPart @Valid @NotNull @NotBlank Customer customer,
            @RequestPart @Valid @NotNull @NotBlank MultipartFile adhar,
            @RequestPart @Valid @NotNull @NotBlank MultipartFile pancard) throws IOException {
        customerService.submitDocuments(customer, adhar.getBytes(), pancard.getBytes());
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer details submitted successfully!");
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

}
