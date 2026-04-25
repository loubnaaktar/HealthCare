package org.example.healthcare.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.healthcare.model.RendezVous;

import java.time.LocalDateTime;
@Data

public class RendezVousDTO {
    private Long id;
    @NotNull
    private LocalDateTime dateRendezVous;
    @NotNull(message = "statut est obligatoire")
    private RendezVous.StatutRendezVous statut;
    private Long idPatient;
    private Long idMedecin;
}
