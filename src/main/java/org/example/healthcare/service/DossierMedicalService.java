package org.example.healthcare.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.healthcare.dto.DossierMedicalDTO;
import org.example.healthcare.mapper.DossierMedicalMapper;
import org.example.healthcare.model.DossierMedical;
import org.example.healthcare.model.Patient;
import org.example.healthcare.repository.DossierMedicalRepository;
import org.example.healthcare.repository.PatientRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DossierMedicalService {
    final DossierMedicalRepository dossierMedicalRepository;
    final DossierMedicalMapper dossierMedicalMapper;
    final PatientRepository patientRepository;

    @CacheEvict(value = "dossiers", allEntries = true)
    public DossierMedicalDTO creerDossierMedical(Long idPatient) {
        Patient patient = patientRepository.findById(idPatient).orElseThrow(() -> new RuntimeException("Patient introuvable"));
        DossierMedical dossierMedical = new DossierMedical();
        dossierMedical.setPatient(patient);
        dossierMedical.setDateCreation(LocalDate.now());
        return dossierMedicalMapper.toDTO(dossierMedicalRepository.save(dossierMedical));
    }

    public DossierMedical chercherDossierMedicalParId(Long idDossier) {
        return dossierMedicalRepository.findById(idDossier).orElseThrow(() -> new RuntimeException("Dossier médical introuvable"));
    }

    @CacheEvict(value = "dossiers", allEntries = true)
    public DossierMedicalDTO ajouterDiagnostic(Long idDossier, String diagnostic) {
        DossierMedical dossierMedical = chercherDossierMedicalParId(idDossier);
        dossierMedical.setDiagnostic(diagnostic);
        return dossierMedicalMapper.toDTO(dossierMedicalRepository.save(dossierMedical));
    }
    @CacheEvict(value = "dossiers", allEntries = true)
    public DossierMedicalDTO ajouterObservation(Long idDossier, String observation) {
        DossierMedical dossierMedical = chercherDossierMedicalParId(idDossier);
        dossierMedical.setObservation(observation);
        return dossierMedicalMapper.toDTO(dossierMedicalRepository.save(dossierMedical));
    }

    @Cacheable(value = "dossiers", key = "#idDossier")
    public DossierMedicalDTO consulterDossierMedicalParId(Long idDossier) {
        return dossierMedicalMapper.toDTO(chercherDossierMedicalParId(idDossier));
    }

    @Cacheable(
            value = "dossiers",
            key = "#pageable.isPaged() ? 'list_' + #pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort : 'list_unpaged'"
    )
    public Page<DossierMedicalDTO> dossierMedicalPage(Pageable pageable){
        Page<DossierMedical> page =  dossierMedicalRepository.findAll(pageable);
        return page.map(dossierMedicalMapper::toDTO);
    }
}
