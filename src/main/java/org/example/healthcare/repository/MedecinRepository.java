package org.example.healthcare.repository;

import org.example.healthcare.model.Medecin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin,Long> {
    Page<Medecin> findAllBySpecialite(String specialite, Pageable pageable);
}
