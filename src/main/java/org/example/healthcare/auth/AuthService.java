package org.example.healthcare.auth;

import lombok.RequiredArgsConstructor;
import org.example.healthcare.model.Medecin;
import org.example.healthcare.model.Patient;
import org.example.healthcare.model.Utilisateur;
import org.example.healthcare.repository.UtilisateurRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UtilisateurRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {
        Utilisateur user;

        if (request.getRole() == Utilisateur.Role.PATIENT) {
            Patient patient = new Patient();
            patient.setNom(request.getNom());
            patient.setPrenom(request.getPrenom());
            patient.setTelephone(request.getTelephone());
            user = patient;
        } else if (request.getRole() == Utilisateur.Role.MEDECIN) {
            Medecin medecin = new Medecin();
            medecin.setNom(request.getNom());
            medecin.setSpecialite(request.getSpecialite());
            medecin.setTelephone(request.getTelephone());
            user = medecin;
        } else {
            user = new Utilisateur();
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        repo.save(user);
        return jwtService.generateToken(user.getEmail(),user);
    }
    public String authenticate(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Utilisateur user =
                repo.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("utilisateur introuvable");
        }
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("mot de passe incorrect");
        }
        return jwtService.generateToken(user.getEmail(),user);
    }
}
