ALTER TABLE patient
    ADD CONSTRAINT fk_patient_utilisateur
        FOREIGN KEY (id) REFERENCES utilisateur(id)
            ON DELETE CASCADE;

ALTER TABLE medecin
    ADD CONSTRAINT fk_medecin_utilisateur
        FOREIGN KEY (id) REFERENCES utilisateur(id)
            ON DELETE CASCADE;