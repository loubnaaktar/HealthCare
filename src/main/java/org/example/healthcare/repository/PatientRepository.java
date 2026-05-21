package org.example.healthcare.repository;

import org.example.healthcare.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    Page<Patient> findAll(Pageable pageable);
    Page<Patient> findAllByNom(String nom, Pageable pageable);
}
