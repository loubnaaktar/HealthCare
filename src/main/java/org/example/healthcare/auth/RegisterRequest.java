package org.example.healthcare.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.healthcare.model.Utilisateur;


@Data
public class RegisterRequest {
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, max = 20,
            message = "Le mot de passe doit contenir entre 8 et 20 caractères")
    private String password;
    @Email(message = "email est obligatoire")
    private String email;

    private Utilisateur.Role role;

    private String nom;
    private String prenom;
    private String telephone;
    private String specialite;
}
