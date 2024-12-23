package org.seclore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DocumentVerificationApp {

    public static void main(String[] args) {
        SpringApplication.run(DocumentVerificationApp.class, args);
    }

}