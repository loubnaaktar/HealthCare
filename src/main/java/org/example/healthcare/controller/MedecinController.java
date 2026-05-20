package org.example.healthcare.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.healthcare.dto.MedecinDTO;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.service.MedecinService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/medecin")
@PreAuthorize("hasRole('ADMIN')")
public class MedecinController {
    private final MedecinService medecinService;





    @GetMapping
    public Page<MedecinDTO> medecinList(  @PageableDefault(sort = "specialite",
            direction = Sort.Direction.ASC)Pageable pageable){
        return medecinService.medecinsList(pageable);
    }

    @PostMapping("/ajouter")
    public MedecinDTO ajouterMedecin(@Valid @RequestBody MedecinDTO medecinDTO){
        return medecinService.ajouterMedecin(medecinDTO);
    }

    @PutMapping("/modifier/{id}")
    public MedecinDTO modifierMedecin( @PathVariable Long id,@Valid @RequestBody MedecinDTO medecinDTO){
        return medecinService.modifierMedecin(id, medecinDTO);
    }

    @DeleteMapping("/supprimer/{id}")
    public void deleteMedecin(@PathVariable Long id){
        medecinService.supprimerMedecin(id);
    }
}
