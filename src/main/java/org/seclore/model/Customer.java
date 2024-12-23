package org.seclore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.seclore.enums.DocumentVerificationStatus;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Date of birth cannot be empty")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Date of birth must be in the format yyyy-MM-dd"
    )
    private String dob;

    private DocumentVerificationStatus status;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] aadharDocument;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] panDocument;
}
