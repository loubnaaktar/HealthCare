package org.example.healthcare.service;

import jakarta.transaction.Transactional;
import org.example.healthcare.dto.PatientDTO;
import org.example.healthcare.model.Patient;
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
class PatientServiceTest {
@Autowired
private PatientService patientService;
@Autowired
private PatientRepository repo;
    PatientDTO dto = new PatientDTO();
@BeforeEach
void initialisation(){

    dto.setNom("el");
    dto.setPrenom("kamal");
    dto.setEmail("mohamed@example.com");
    dto.setTelephone("0612345678");
    dto.setDateNaissance(LocalDate.of(1998, 5, 15));
}
    @Test
    void ajouterPatient() {
    PatientDTO patientDTO = patientService.ajouterPatient(dto);
    assertNotNull(patientDTO);
    assertEquals("kamal",patientDTO.getPrenom());
    }


}