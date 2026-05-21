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
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.List;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/patient")
public class PatientController {
    private final PatientService patientService;

    @GetMapping

    public Page<PatientDTO> getPatients(  @PageableDefault(sort = "nom",
            direction = Sort.Direction.ASC) Pageable pageabl) {
        return patientService.patientsList(pageabl);
    }
    @PostMapping("/ajouter")
    public PatientDTO ajouterPatient(@Valid @RequestBody PatientDTO patientDTO) {
       return patientService.ajouterPatient(patientDTO);
    }

    @PutMapping("/modifier/{id}")
    public PatientDTO modifierPatient( @PathVariable Long id,@Valid @RequestBody PatientDTO patientDTO) {
return patientService.modifierPatient(id,patientDTO);
    }

    @DeleteMapping("/supprimer/{id}")
    public void supprimerPatient(@PathVariable Long id) {
        patientService.supprimerPatient(id);
    }

    @GetMapping("/chercher/{id}")
    public PatientDTO chercherPatient(@PathVariable Long id) {
        return patientService.consulterPatient(id);
    }

    @GetMapping("/Chercher_par_nom/{nom}")
    public Page<PatientDTO> patientParNom(@PathVariable String nom ,@PageableDefault(direction = Sort.Direction.ASC) Pageable pageable){
        return patientService.PageParNomPatient(nom,pageable);
    }
}
