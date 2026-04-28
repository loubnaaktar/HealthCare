package org.example.healthcare.service;

import jakarta.transaction.Transactional;
import org.example.healthcare.dto.MedecinDTO;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.repository.MedecinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MedecinServiceTest {

@Autowired
private MedecinService medecinService;
@Autowired
private MedecinRepository repo;


Medecin medecin = new Medecin();
@BeforeEach
void initialisation(){
    medecin.setNom("Dr ahmed");
    medecin.setEmail("ahmed@example.com");
    medecin.setTelephone("0612345678");
    medecin.setSpecialite("Cardiologie");
    medecin = repo.save(medecin);
}
    @Test
    void modifierMedecin() {
        MedecinDTO dto = new MedecinDTO();
        medecin.setNom("Dr salah");
        medecin.setEmail("salah@example.com");
        medecin.setTelephone("0612345679");
        medecin.setSpecialite("Cardiologie");
        MedecinDTO medecinDTO = medecinService.modifierMedecin(medecin.getId(),dto);

        assertEquals(medecinDTO.getNom(),medecin.getNom());

    }


    @Test
    void supprimerMedecin() {
    medecinService.supprimerMedecin(medecin.getId());
    assertTrue(repo.findById(medecin.getId()).isEmpty());
    }
}