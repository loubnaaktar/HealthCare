package org.example.healthcare.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.healthcare.dto.MedecinDTO;
import org.example.healthcare.dto.PatientDTO;
import org.example.healthcare.mapper.MedecinMapper;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.repository.MedecinRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.cfg.MapperBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedecinService {
    private final MedecinRepository medecinRepository;
    private final MedecinMapper medecinMapper;

    public MedecinDTO ajouterMedecin(MedecinDTO medecinDTO){
        Medecin medecin = medecinMapper.toEntity(medecinDTO);
        return medecinMapper.toDTO(medecinRepository.save(medecin));
    }

    public MedecinDTO modifierMedecin(Long idMedecin, MedecinDTO medecinDTO){
        Medecin medecin = chercherMedecinParId(idMedecin);
        medecinMapper.modifierMedecinDTO(medecinDTO,medecin);
        return medecinMapper.toDTO(medecinRepository.save(medecin));
    }

    public Medecin chercherMedecinParId(Long idMedecin){
     return medecinRepository.findById(idMedecin).orElseThrow(()-> new RuntimeException("Médecin introuvable"));
    }

    public void supprimerMedecin(Long idMedecin){
        Medecin medecin = chercherMedecinParId(idMedecin);
        medecinRepository.delete(medecin);
    }

    public Page<MedecinDTO> medecinsList(Pageable pageable){
        Page<Medecin> medecins = medecinRepository.findAll(pageable);
        return medecins.map(medecinMapper::toDTO);
    }

    public Page<MedecinDTO> medecinParSpecialite(String specialite, Pageable pageable){
        Page<Medecin> medecinPage = medecinRepository.findAllBySpecialite(specialite,pageable);
        return medecinPage.map(medecinMapper::toDTO);
    }
}
