package org.seclore.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.seclore.enums.DocumentVerificationStatus;
import org.seclore.model.Customer;
import org.seclore.repository.CustomerRepo;
import org.seclore.service.OcrService;
import org.seclore.util.DocumentMatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentVerificationScheduler {

    private final CustomerRepo customerRepository;
    private final OcrService ocrService;

    @Scheduled(fixedRate = 60000) // Every 1 minute
    public void verifyDocuments() {
        List<Customer> pendingCustomers = customerRepository.findAllByStatus(DocumentVerificationStatus.SUBMITTED);
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
            log.info("Customer verification done for id: {}", customer.getId());
        }
    }
}
