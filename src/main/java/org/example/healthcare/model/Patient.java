package org.example.healthcare.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Patient extends Utilisateur {

    private String nom;
    private String prenom;
    private String telephone;
    private LocalDate dateNaissance;

    @OneToMany(mappedBy = "patient")
    private List<RendezVous> rendezVousList = new ArrayList<>();

    @OneToOne(mappedBy = "patient")
    private DossierMedical dossierMedical;
}