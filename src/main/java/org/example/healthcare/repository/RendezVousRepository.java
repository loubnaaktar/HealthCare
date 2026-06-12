package org.example.healthcare.repository;

import org.example.healthcare.model.RendezVous;
import org.example.healthcare.model.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous,Long> {

    Page<RendezVous> findAll(Pageable pageable);

    Page<RendezVous> findByPatient_Id(Long idPatient, org.springframework.data.domain.Pageable pageable);

    List<RendezVous> findByPatient_Id(Long idPatient);

    Page<RendezVous> findByMedecin_Id(Long idMedecin, org.springframework.data.domain.Pageable pageable);

    Page<RendezVous> findAllByStatut(RendezVous.StatutRendezVous statutRendezVous, Pageable pageable);
}
