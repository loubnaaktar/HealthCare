package org.example.healthcare.service;

import jakarta.transaction.Transactional;
import org.example.healthcare.dto.DossierMedicalDTO;
import org.example.healthcare.model.DossierMedical;
import org.example.healthcare.model.Patient;
import org.example.healthcare.repository.DossierMedicalRepository;
import org.example.healthcare.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class DossierMedicalServiceTest {
@Autowired
private DossierMedicalService dossierMedicalService;
@Autowired
private DossierMedicalRepository repo;
@Autowired
private PatientRepository patientRepo;
    Patient patient = new Patient();
    @BeforeEach
    void initialisation(){
        patient.setNom("el");
        patient.setPrenom("kamal");
        patient.setEmail("mohamed@example.com");
        patient.setTelephone("0612345678");
        patient.setDateNaissance(LocalDate.of(1998, 5, 15));
        patient = patientRepo.save(patient);
    }
    @Test
    void creerDossierMedical() {

        DossierMedicalDTO dossier = dossierMedicalService.creerDossierMedical(patient.getId());
        assertNotNull(dossier);
        assertEquals(LocalDate.now(),dossier.getDateCreation());
    }

    @Test
    void ajouterDiagnostic() {
        DossierMedical dossier = new DossierMedical();
        dossier.setDateCreation(LocalDate.now());
        dossier.setPatient(patient);
        dossier = repo.save(dossier);

       DossierMedicalDTO dto = dossierMedicalService.ajouterDiagnostic(dossier.getId(),"maladie grave");
       assertEquals("maladie grave",dto.getDiagnostic());
    }
}