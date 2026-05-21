package org.example.healthcare.service;

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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.healthcare.model.RendezVous.StatutRendezVous.*;

@Service
@RequiredArgsConstructor
public class RendezVousService {
    private final RendezVousMapper rendezVousMapper;
    private final RendezVousRepository rendezVousRepository;
    private final MedecinRepository medecinRepository;
    private final PatientRepository patientRepository;

    public RendezVousDTO creerRendezVous(Long idPatient, Long idMedecin, LocalDateTime date) {
        if(date.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date du rendez-vous ne peut pas être dans le passé");
        }
        Medecin medecin = medecinRepository.findById(idMedecin).orElseThrow(() -> new RuntimeException("Médecin introuvable"));
        Patient patient = patientRepository.findById(idPatient).orElseThrow(() -> new RuntimeException("Patient introuvable"));
        RendezVous rendezVous = new RendezVous();
        rendezVous.setMedecin(medecin);
        rendezVous.setPatient(patient);
        rendezVous.setDateRendezVous(date);
        rendezVous.setStatut(PLANIFIE);
        return rendezVousMapper.toDTO(rendezVousRepository.save(rendezVous));
    }

    public RendezVousDTO modifierRendezVous(Long idRendzVous, RendezVousDTO rendezVousDTO) {

        RendezVous rendezVous = chercherRendezVous(idRendzVous);

        if (rendezVous.getStatut() == TERMINE ||
                rendezVous.getStatut() == ANNULE) {
            throw new IllegalStateException("Impossible de modifier un rendez-vous terminé ou annulé");
        }
        if (rendezVousDTO.getDateRendezVous() != null) {
            rendezVous.setDateRendezVous(rendezVousDTO.getDateRendezVous());
        }
        if (rendezVousDTO.getStatut() != null) {
            rendezVous.setStatut(rendezVousDTO.getStatut());
        }
        if (rendezVousDTO.getIdPatient() != null) {
            Patient newPatient = patientRepository.findById(rendezVousDTO.getIdPatient()).orElseThrow(() -> new RuntimeException("Patient introuvable"));
            rendezVous.setPatient(newPatient);
        }
        return rendezVousMapper.toDTO(rendezVousRepository.save(rendezVous));

    }

    private RendezVous chercherRendezVous(Long idRendezVous) {
        return rendezVousRepository.findById(idRendezVous).orElseThrow(() -> new RuntimeException("RendezVous introuvable"));
    }

    public RendezVousDTO annulerRendezVous(Long idRendezVous) {
        RendezVous rendezVous = chercherRendezVous(idRendezVous);
        if (rendezVous.getStatut() == TERMINE) {
            throw new IllegalStateException("Rendez-vous déjà terminé");
        }
        if (rendezVous.getStatut() == ANNULE) {
            throw new IllegalStateException("Rendez-vous déjà annulé");
        }
        rendezVous.setStatut(ANNULE);
        return rendezVousMapper.toDTO(rendezVousRepository.save(rendezVous));
    }

    public Page<RendezVousDTO> rendezVousDTOList(Pageable pageable){
        Page<RendezVous> rendezVousPage = rendezVousRepository.findAll(pageable);
        return rendezVousPage.map(rendezVousMapper::toDTO);
    }

    public Page<RendezVousDTO> rendezVousDtoPatient(Long idPatient, Pageable pageable){
        Page<RendezVous> pageRv = rendezVousRepository.findByPatient_Id(idPatient,pageable);
        return pageRv.map(rendezVousMapper::toDTO);
    }

    public Page<RendezVousDTO> rendezVousDtoMedecin(Long idMedecin, Pageable pageable){
        Page<RendezVous> list = rendezVousRepository.findByMedecin_Id(idMedecin,pageable);
        return list.map(rendezVousMapper::toDTO);
    }

    public Page<RendezVousDTO> rendezVousDTOSParStatut(RendezVous.StatutRendezVous statut , Pageable pageable){
        Page<RendezVous> rendezVousDTOPage = rendezVousRepository.findAllByStatut(statut, pageable);
        return rendezVousDTOPage.map(rendezVousMapper::toDTO);
    }
}
