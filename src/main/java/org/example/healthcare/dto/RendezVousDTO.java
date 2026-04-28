package org.example.healthcare.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.healthcare.model.RendezVous;

import java.time.LocalDateTime;
@Data

public class RendezVousDTO {
    private Long id;
    @NotNull
    @Future
    private LocalDateTime dateRendezVous;
    @NotNull(message = "statut est obligatoire")
    private RendezVous.StatutRendezVous statut;
    @NotNull
    private Long idPatient;
    @NotNull
    private Long idMedecin;
}
