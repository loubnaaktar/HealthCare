package org.example.healthcare.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.healthcare.dto.DossierMedicalDTO;
import org.example.healthcare.service.DossierMedicalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController

@RequestMapping("/api/dossierMedical")
public class DossierMedicalController {
    private final DossierMedicalService dossierMedicalService;

    @GetMapping
    @PreAuthorize("hasRole('MEDECIN')")
    public Page<DossierMedicalDTO> dossierMedicalDTOPage(Pageable pageable){
        return dossierMedicalService.dossierMedicalPage(pageable);
    }
    @PreAuthorize("hasAnyRole('PATIENT','MEDECIN')")
    @GetMapping("/consulter/{id}")
    public DossierMedicalDTO consulterDM(@PathVariable Long id) {
        return dossierMedicalService.consulterDossierMedicalParId(id);
    }

    @PreAuthorize("hasRole('MEDECIN')")
    @PostMapping("/creer/{idPatient}")
    public DossierMedicalDTO creerDossierMedical(@PathVariable Long idPatient) {
        return dossierMedicalService.creerDossierMedical(idPatient);
    }
     @PreAuthorize("hasRole('MEDECIN')")
    @PostMapping("/ajouterDiagnostic/{idDossier}")
    public DossierMedicalDTO ajouterDiagnostic(@PathVariable Long idDossier,@Valid @RequestParam String diagnostic) {
        return dossierMedicalService.ajouterDiagnostic(idDossier,diagnostic);
    }
     @PreAuthorize("hasRole('MEDECIN')")
    @PostMapping("/ajouterObservation/{idDossier}")
    public DossierMedicalDTO ajouterObservation(@PathVariable Long idDossier,@Valid @RequestParam String observation) {
        return dossierMedicalService.ajouterObservation(idDossier,observation);
    }
}
