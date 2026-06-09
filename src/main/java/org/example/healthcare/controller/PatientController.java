package org.example.healthcare.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.healthcare.dto.PatientDTO;
import org.example.healthcare.model.Patient;
import org.example.healthcare.service.PatientService;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.List;

@AllArgsConstructor
@RestController

@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<PatientDTO> getPatients(  @PageableDefault(sort = "nom",
            direction = Sort.Direction.ASC) Pageable pageabl) {
        return patientService.patientsList(pageabl);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ajouter")
    public PatientDTO ajouterPatient(@Valid @RequestBody PatientDTO patientDTO) {
       return patientService.ajouterPatient(patientDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/modifier/{id}")
    public PatientDTO modifierPatient( @PathVariable Long id,@Valid @RequestBody PatientDTO patientDTO) {
return patientService.modifierPatient(id,patientDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/supprimer/{id}")
    public void supprimerPatient(@PathVariable Long id) {
        patientService.supprimerPatient(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chercher/{id}")
    public PatientDTO chercherPatient(@PathVariable Long id) {
        return patientService.consulterPatient(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Chercher_par_nom/{nom}")
    public Page<PatientDTO> patientParNom(@PathVariable String nom ,@PageableDefault(direction = Sort.Direction.ASC) Pageable pageable){
        return patientService.PageParNomPatient(nom,pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PATIENT')")
    public PatientDTO getPatient(@PathVariable Long id,
                                 Authentication authentication){

        return patientService.getPatient(
                id,
                authentication.getName()
        );
    }
}
