package org.example.healthcare.pdf;

import lombok.RequiredArgsConstructor;
import org.example.healthcare.repository.MedecinRepository;
import org.example.healthcare.repository.PatientRepository;
import org.example.healthcare.repository.RendezVousRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pdf")
public class PdfStatsController {

    private final PdfService pdfService;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final RendezVousRepository rendezVousRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/rapport-global")
    public ResponseEntity<byte[]> downloadGlobalStatsReport() {
        long patientCount = patientRepository.count();
        long medecinCount = medecinRepository.count();
        long rdvCount = rendezVousRepository.count();

        byte[] pdf = pdfService.generateStatsPdf(patientCount, medecinCount, rdvCount);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport_statistique_global.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
