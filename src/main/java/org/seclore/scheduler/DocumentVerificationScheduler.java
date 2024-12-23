package org.seclore.scheduler;

import org.seclore.enums.DocumentVerificationStatus;
import org.seclore.model.Customer;
import org.seclore.repository.CustomerRepo;
import org.seclore.service.OcrService;
import org.seclore.util.DocumentMatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentVerificationScheduler {

    private final CustomerRepo customerRepository;
    private final OcrService ocrService;

    public DocumentVerificationScheduler(CustomerRepo customerRepository,
                                         @Qualifier("tesseract") OcrService ocrService) {
        this.customerRepository = customerRepository;
        this.ocrService = ocrService;
    }

    @Scheduled(fixedRate = 60000) // Every 1 minute
    public void verifyDocuments() {
        List<Customer> pendingCustomers = customerRepository.findAll()
                .stream()
                .filter(c -> DocumentVerificationStatus.SUBMITTED.equals(c.getStatus()))
                .toList();
        for (Customer customer : pendingCustomers) {
            String aadharText = ocrService.extractTextFromImage(customer.getAadharDocument());
            String panText = ocrService.extractTextFromImage(customer.getPanDocument());
            if (DocumentMatcher.matchDetails(customer, aadharText) &&
                    DocumentMatcher.matchDetails(customer, panText)) {
                customer.setStatus(DocumentVerificationStatus.APPROVED);
            } else {
                customer.setStatus(DocumentVerificationStatus.REJECTED);
            }
            customerRepository.save(customer);
        }
    }
}
