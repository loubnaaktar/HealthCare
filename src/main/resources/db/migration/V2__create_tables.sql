CREATE TABLE patient (
                         id BIGINT PRIMARY KEY,
                         nom VARCHAR(50) NOT NULL,
                         prenom VARCHAR(50) NOT NULL,
                         telephone VARCHAR(100) NOT NULL,
                         date_naissance DATE,
                         CONSTRAINT fk_patient_utilisateur
                             FOREIGN KEY (id) REFERENCES utilisateur(id)
                                 ON DELETE CASCADE
);

CREATE TABLE medecin (
                         id BIGINT PRIMARY KEY,
                         nom VARCHAR(50) NOT NULL,
                         specialite VARCHAR(50) NOT NULL,
                         telephone VARCHAR(100) NOT NULL,
                         CONSTRAINT fk_medecin_utilisateur
                             FOREIGN KEY (id) REFERENCES utilisateur(id)
                                 ON DELETE CASCADE
);

CREATE TABLE dossier_medical (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 diagnostic TEXT,
                                 observation TEXT,
                                 date_creation DATE NOT NULL,
                                 patient_id BIGINT UNIQUE,
                                 CONSTRAINT fk_dossier_patient
                                     FOREIGN KEY (patient_id) REFERENCES patient(id)
                                         ON DELETE CASCADE
);

CREATE TABLE rendez_vous (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             date_rendez_vous DATETIME NOT NULL,
                             statut VARCHAR(50) NOT NULL DEFAULT 'PLANIFIE',
                             patient_id BIGINT,
                             medecin_id BIGINT,
                             CONSTRAINT fk_rdv_patient
                                 FOREIGN KEY (patient_id) REFERENCES patient(id),
                             CONSTRAINT fk_rdv_medecin
                                 FOREIGN KEY (medecin_id) REFERENCES medecin(id)
);