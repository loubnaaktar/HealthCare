package org.example.healthcare.repository;

import org.example.healthcare.model.DossierMedical;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierMedicalRepository extends JpaRepository<DossierMedical,Long> {
    Page<DossierMedical> findAll(Pageable pageable);
}
