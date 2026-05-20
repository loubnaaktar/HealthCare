package org.example.healthcare.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.healthcare.dto.RendezVousDTO;
import org.example.healthcare.model.RendezVous;
import org.example.healthcare.service.RendezVousService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/rendezVous")
public class RendezVousController {
    private final RendezVousService rendezVousService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")

    public Page<RendezVousDTO> rendezVousList(  @PageableDefault(sort = "dateRendezVous",
            direction = Sort.Direction.ASC)Pageable pageable){
        return rendezVousService.rendezVousDTOList(pageable);
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/rendezVousPatient/{idPatient}")
    public Page<RendezVousDTO> rendezVousPatient(@PathVariable Long idPatient,Pageable pageable){
        return rendezVousService.rendezVousDtoPatient(idPatient,pageable);
    }
    @PreAuthorize("hasRole('MEDECIN')")
    @GetMapping("/rendezVousMedecin/{idMedecin}")
    public Page<RendezVousDTO>  rendezVousMedecin(@PathVariable Long idMedecin,Pageable pageable){
        return rendezVousService.rendezVousDtoMedecin(idMedecin,pageable);
    }
    @PreAuthorize("hasRole('MEDECIN')")
    @PostMapping("/CreerRendzVous")
    public RendezVousDTO creerRendezVous(@Valid @RequestBody RendezVousDTO rendezVousDTO){
        return rendezVousService.creerRendezVous(
                rendezVousDTO.getIdPatient(),
                rendezVousDTO.getIdMedecin(),
                rendezVousDTO.getDateRendezVous()
        );
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/AnnulerRendzVous/{idRendezVous}")
    public RendezVousDTO annulerRendeVous(@PathVariable Long idRendezVous){
        return rendezVousService.annulerRendezVous(idRendezVous);
    }

    @PreAuthorize("hasRole('MEDECIN')")
    @PatchMapping("/modifier/{idRendezVous}")
    public RendezVousDTO modifierRendezVous( @PathVariable Long idRendezVous,@Valid @RequestBody RendezVousDTO rendezVousDTO){
        return rendezVousService.modifierRendezVous(idRendezVous,rendezVousDTO);
    }




}
