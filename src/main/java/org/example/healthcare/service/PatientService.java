package org.example.healthcare.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.healthcare.dto.PatientDTO;
import org.example.healthcare.mapper.PatientMapper;
import org.example.healthcare.model.Patient;
import org.example.healthcare.repository.PatientRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tools.jackson.databind.cfg.MapperBuilder;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

private final PatientRepository patientRepository;
private final PatientMapper  patientMapper;


    @CacheEvict(value = "patients", allEntries = true)
    public PatientDTO ajouterPatient(PatientDTO patientDTO) {

    Patient patient = patientMapper.toEntity(patientDTO);
    return patientMapper.toDTO(patientRepository.save(patient));
}

    @Caching(evict = {
            @CacheEvict(value = "patients", key = "#idPatient"),
            @CacheEvict(value = "patients", allEntries = true)
    })
public PatientDTO modifierPatient(Long idPatient, PatientDTO patientDTO) {
Patient patient = chercherPatientParId(idPatient);
patientMapper.modifierPatient(patientDTO,patient);
return patientMapper.toDTO(patientRepository.save(patient));
}

public Patient chercherPatientParId(Long idPatient) {
   return patientRepository.findById(idPatient).orElseThrow(()-> new RuntimeException("Patient introuvable."));
}

    @Caching(evict = {
            @CacheEvict(value = "patients", key = "#idPatient"),
            @CacheEvict(value = "patients", allEntries = true)
    })
public void supprimerPatient(Long idPatient) {
        Patient patient = chercherPatientParId(idPatient);
        patientRepository.delete(patient);
}

    @Cacheable(
            value = "patients",
            key = "'list_' + #pageable.pageNumber + '_' + #pageable.pageSize"
    )
    public Page<PatientDTO> patientsList(Pageable pageable){
        System.out.println("list de patients cache");
        Page<Patient> patients = patientRepository.findAll(pageable);
        return patients.map(patientMapper::toDTO);
    }

public PatientDTO consulterPatient(Long idPatient) {
        return  patientMapper.toDTO(chercherPatientParId(idPatient));
}

    @Cacheable(
            value = "patients",
            key = "'nom_' + #nom + '_' + #pageable.pageNumber + '_' + #pageable.pageSize"
    )
public Page<PatientDTO> PageParNomPatient(String nom, Pageable pageable){
        Page<Patient> patientPage = patientRepository.findAllByNom(nom,pageable);
        return patientPage.map(patientMapper::toDTO);
}

    public PatientDTO getPatient(Long id, String email){

        Patient patient = patientRepository
                .findByIdAndEmail(id, email)
                .orElseThrow(() ->
                        new RuntimeException("vous ne pouvez pas consulter ce patient "));

        return patientMapper.toDTO(patient);
    }
}
