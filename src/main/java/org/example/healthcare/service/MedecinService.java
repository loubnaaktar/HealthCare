package org.example.healthcare.service;

import lombok.RequiredArgsConstructor;
import org.example.healthcare.dto.MedecinDTO;
import org.example.healthcare.mapper.MedecinMapper;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.repository.MedecinRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedecinService {
    private final MedecinRepository medecinRepository;
    private final MedecinMapper medecinMapper;

    @CacheEvict(value = "medecins", allEntries = true)
    public MedecinDTO ajouterMedecin(MedecinDTO medecinDTO){
        Medecin medecin = medecinMapper.toEntity(medecinDTO);
        return medecinMapper.toDTO(medecinRepository.save(medecin));
    }

    @Caching(evict = {
            @CacheEvict(value = "medecins", key = "#idMedecin"),
            @CacheEvict(value = "medecins", allEntries = true)
    })
    public MedecinDTO modifierMedecin(Long idMedecin, MedecinDTO medecinDTO){
        Medecin medecin = chercherMedecinParId(idMedecin);
        medecinMapper.modifierMedecinDTO(medecinDTO,medecin);
        return medecinMapper.toDTO(medecinRepository.save(medecin));
    }

    public Medecin chercherMedecinParId(Long idMedecin){
     return medecinRepository.findById(idMedecin).orElseThrow(()-> new RuntimeException("Médecin introuvable"));
    }

    @Caching(evict = {
            @CacheEvict(value = "medecins", key = "#idMedecin"),
            @CacheEvict(value = "medecins", allEntries = true)
    })
    public void supprimerMedecin(Long idMedecin){
        Medecin medecin = chercherMedecinParId(idMedecin);
        medecinRepository.delete(medecin);
    }

    @Cacheable(
            value = "medecins",
            key = "'list_' + #pageable.pageNumber + '_' + #pageable.pageSize"
    )
    public Page<MedecinDTO> medecinsList(Pageable pageable){
        System.out.println("list de médecins cachée ");
        Page<Medecin> medecins = medecinRepository.findAll(pageable);
        return medecins.map(medecinMapper::toDTO);
    }

    @Cacheable(
            value = "medecins",
            key = "'spec_' + #specialite + '_' + #pageable.pageNumber + '_' + #pageable.pageSize"
    )
    public Page<MedecinDTO> medecinParSpecialite(String specialite, Pageable pageable){
        Page<Medecin> medecinPage = medecinRepository.findAllBySpecialite(specialite,pageable);
        return medecinPage.map(medecinMapper::toDTO);
    }

}
