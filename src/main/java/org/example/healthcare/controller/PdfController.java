package org.example.healthcare.controller;

import lombok.RequiredArgsConstructor;
import org.example.healthcare.dto.RendezVousDTO;
import org.example.healthcare.service.PdfService;
import org.example.healthcare.service.RendezVousService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<byte[]> downloadPdf(Pageable pageable) {


      Page<RendezVousDTO> rdvsPage = rendezVousService.rendezVousDTOList(pageable);
      List<RendezVousDTO> rdvs = rdvsPage.getContent();

        byte[] pdf = pdfService.generateRendezVousPdf(rdvs);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rendezvous.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}