package org.example.healthcare.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.healthcare.dto.PatientDTO;
import org.example.healthcare.mapper.PatientMapper;
import org.example.healthcare.model.Patient;
import org.example.healthcare.repository.PatientRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.cfg.MapperBuilder;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {

private final PatientRepository patientRepository;
private final PatientMapper  patientMapper;


    public PatientDTO ajouterPatient(PatientDTO patientDTO) {

    Patient patient = patientMapper.toEntity(patientDTO);
    return patientMapper.toDTO(patientRepository.save(patient));
}
public PatientDTO modifierPatient(Long idPatient, PatientDTO patientDTO) {
Patient patient = patientMapper.toEntity(chercherPatientParId(idPatient));
patientMapper.modifierPatient(patientDTO,patient);
return patientMapper.toDTO(patientRepository.save(patient));
}

public PatientDTO chercherPatientParId(Long idPatient) {
    Patient patient = patientRepository.findById(idPatient).orElseThrow(()-> new RuntimeException("Patient introuvable."));
    return patientMapper.toDTO(patient);
}

public void supprimerPatient(Long idPatient) {
        Patient patient = patientMapper.toEntity(chercherPatientParId(idPatient));
        patientRepository.delete(patient);
}

public List<PatientDTO> patientsList(){
        List<Patient> patients = patientRepository.findAll();
        return patientMapper.toDTO(patients);
}
}
