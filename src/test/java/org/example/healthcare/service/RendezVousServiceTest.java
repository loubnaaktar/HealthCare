package org.example.healthcare.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.healthcare.dto.RendezVousDTO;
import org.example.healthcare.mapper.RendezVousMapper;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.model.Patient;
import org.example.healthcare.model.RendezVous;
import org.example.healthcare.repository.MedecinRepository;
import org.example.healthcare.repository.PatientRepository;
import org.example.healthcare.repository.RendezVousRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RendezVousServiceTest {

@Autowired
    private RendezVousService rvService;
@Autowired
    private MedecinRepository medecinRepository;
@Autowired
    private PatientRepository patientRepository;
@Autowired
    private RendezVousMapper mapper;
@Autowired
    private RendezVousRepository rvRepo;
@Autowired
    private RendezVousService rendezVousService;

    Medecin medecin = new Medecin();
    Patient patient = new Patient();


    @Test

    void creerRendezVous() {
        medecin.setNom("Dr ahmed");
        medecin.setEmail("ahmed@example.com");
        medecin.setTelephone("0612345678");
        medecin.setSpecialite("Cardiologie");
        medecin = medecinRepository.save(medecin);

        patient.setNom("el");
        patient.setPrenom("kamal");
        patient.setEmail("mohamed@example.com");
        patient.setTelephone("0612345678");
        patient.setDateNaissance(LocalDate.of(1998, 5, 15));
        patient = patientRepository.save(patient);

        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        RendezVousDTO dto = rvService.creerRendezVous(patient.getId(),medecin.getId(),futureDate);

        assertNotNull(dto);
        assertEquals(RendezVous.StatutRendezVous.PLANIFIE,dto.getStatut());
    }

    @Test
    void modifierRendezVous() {
        RendezVous rv = new RendezVous();
        rv.setDateRendezVous(LocalDateTime.of(2025, 12, 20, 14, 30));
        rv.setMedecin(medecin);
        rv.setPatient(patient);
        rv.setStatut(RendezVous.StatutRendezVous.CONFIRME);
        rv = rvRepo.save(rv);

        RendezVousDTO rvDTO = mapper.toDTO(rv);
        rvDTO.setStatut(RendezVous.StatutRendezVous.EN_CONSULTATION);

        RendezVousDTO dto = rendezVousService.modifierRendezVous(rv.getId(),rvDTO);

        assertEquals(RendezVous.StatutRendezVous.EN_CONSULTATION,dto.getStatut());
    }


    @Test
    void rendezVousDtoPatient() {
        patient.setNom("el");
        patient.setPrenom("kamal");
        patient.setEmail("mohamed@example.com");
        patient.setTelephone("0612345678");
        patient.setDateNaissance(LocalDate.of(1998, 5, 15));
        patient = patientRepository.save(patient);

    RendezVousDTO rv1 = new RendezVousDTO();
    rv1.setDateRendezVous( LocalDateTime.of(2025, 12, 20, 9, 0));
    rv1.setIdPatient(patient.getId());
    RendezVousDTO rv2 = new RendezVousDTO();
    rv2.setDateRendezVous( LocalDateTime.of(2025, 12, 21, 14, 30));
    rv2.setIdPatient(patient.getId());
    RendezVousDTO rv3 = new RendezVousDTO();
    rv3.setDateRendezVous(LocalDateTime.of(2025, 12, 22, 16, 45));
    rv3.setIdPatient(patient.getId());

    List<RendezVousDTO> list = List.of(rv1,rv2,rv3);

    assertEquals(3,list.size());

    }

    @Test
    void annulerRendezVous() {
        RendezVous rv = new RendezVous();
        rv.setDateRendezVous(LocalDateTime.of(2025, 12, 20, 14, 30));
        rv.setStatut(RendezVous.StatutRendezVous.CONFIRME);
        rv = rvRepo.save(rv);


        RendezVousDTO dto = rendezVousService.annulerRendezVous(rv.getId());

        assertEquals(RendezVous.StatutRendezVous.ANNULE,dto.getStatut());

    }
}