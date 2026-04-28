CREATE TABLE patient (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nom VARCHAR(50) NOT NULL,
                         prenom VARCHAR(50) NOT NULL,
                         email VARCHAR(150) UNIQUE NOT NULL,
                         telephone VARCHAR(100) NOT NULL,
                         date_naissance DATE
);

CREATE TABLE medecin (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         nom VARCHAR(50) NOT NULL,
                         specialite VARCHAR(50) NOT NULL,
                         email VARCHAR(150) UNIQUE NOT NULL,
                         telephone VARCHAR(100) NOT NULL
);

CREATE TABLE dossier_medical (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 diagnostic TEXT,
                                 observation TEXT,
                                 date_creation DATE NOT NULL,
                                 patient_id BIGINT NOT NULL UNIQUE,

                                 CONSTRAINT fk_dossier_patient
                                     FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE TABLE rendez_vous (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             date_rendez_vous DATETIME NOT NULL,
                             statut ENUM ('PLANIFIE','CONFIRME','EN_CONSULTATION','TERMINE','ANNULE','REPROGRAMME')
        NOT NULL DEFAULT 'PLANIFIE',
                             patient_id BIGINT,
                             medecin_id BIGINT,

                             CONSTRAINT fk_rdv_patient
                                 FOREIGN KEY (patient_id) REFERENCES patient(id),

                             CONSTRAINT fk_rdv_medecin
                                 FOREIGN KEY (medecin_id) REFERENCES medecin(id)
);