package org.example.healthcare.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
@Data
public class  PatientDTO {
    private Long id;
    @NotBlank(message ="le nom est obligatoire")
    private String nom;
    @NotBlank(message ="le prénom est obligatoire")
    private String prenom;
    @Email(message = "email est obligatoire")
    private String email;
    @Size(min = 10, max = 10, message = "numero doit étre mois de 10")
    private String telephone;
    @NotNull
    @Past
    private LocalDate dateNaissance;
}
