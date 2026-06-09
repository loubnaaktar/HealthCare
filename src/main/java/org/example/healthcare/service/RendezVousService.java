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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @CacheEvict(value = "rendezvous", allEntries = true)
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

    @CacheEvict(value = "rendezvous", allEntries = true)
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

    @Cacheable(value = "rendezvous", key = "#idRendezVous")
    private RendezVous chercherRendezVous(Long idRendezVous) {
        return rendezVousRepository.findById(idRendezVous).orElseThrow(() -> new RuntimeException("RendezVous introuvable"));
    }

    @CacheEvict(value = "rendezvous", allEntries = true)
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

    @Cacheable(
            value = "rendezvous",
            key = "#pageable.isPaged() ? 'list_' + #pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort : 'list_unpaged'"
    )
    public Page<RendezVousDTO> rendezVousDTOList(Pageable pageable){
        Page<RendezVous> rendezVousPage = rendezVousRepository.findAll(pageable);
        return rendezVousPage.map(rendezVousMapper::toDTO);
    }

    @Cacheable(
            value = "rendezvous",
            key = "#pageable.isPaged() ? 'patient_' + #idPatient + '_' + #pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort : 'patient_' + #idPatient + '_unpaged'"
    )
    public Page<RendezVousDTO> rendezVousDtoPatient(Long idPatient, Pageable pageable){
        Page<RendezVous> pageRv = rendezVousRepository.findByPatient_Id(idPatient,pageable);
        return pageRv.map(rendezVousMapper::toDTO);
    }

    @Cacheable(
            value = "rendezvous",
            key = "#pageable.isPaged() ? 'medecin_' + #idMedecin + '_' + #pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort : 'medecin_' + #idMedecin + '_unpaged'"
    )
    public Page<RendezVousDTO> rendezVousDtoMedecin(Long idMedecin, Pageable pageable){
        Page<RendezVous> list = rendezVousRepository.findByMedecin_Id(idMedecin,pageable);
        return list.map(rendezVousMapper::toDTO);
    }

    @Cacheable(
            value = "rendezvous",
            key = "#pageable.isPaged() ? 'statut_' + #statut + '_' + #pageable.pageNumber + '_' + #pageable.pageSize + '_' + #pageable.sort : 'statut_' + #statut + '_unpaged'"
    )
    public Page<RendezVousDTO> rendezVousDTOSParStatut(RendezVous.StatutRendezVous statut , Pageable pageable){
        Page<RendezVous> rendezVousDTOPage = rendezVousRepository.findAllByStatut(statut, pageable);
        return rendezVousDTOPage.map(rendezVousMapper::toDTO);
    }
}
