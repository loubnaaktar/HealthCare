package org.example.healthcare.controller;

import lombok.RequiredArgsConstructor;
import org.example.healthcare.dto.RendezVousDTO;
import org.example.healthcare.service.PdfService;
import org.example.healthcare.service.RendezVousService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pdf")
public class PdfController {

    private final PdfService pdfService;
    private final RendezVousService rendezVousService;

    @PreAuthorize("hasAnyRole('ADMIN','MEDECIN','PATIENT')")
    @GetMapping("/rendezvous")
    public ResponseEntity<InputStreamResource> downloadRendezVous() {

        List<RendezVousDTO> list = rendezVousService
                .rendezVousDTOList(Pageable.unpaged())
                .getContent();

        ByteArrayInputStream pdf = pdfService.generateRendezVousPdf(list);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=rendezvous.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}