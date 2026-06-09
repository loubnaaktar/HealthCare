package org.example.healthcare.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.example.healthcare.dto.RendezVousDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
@Service
public class PdfService {

    public ByteArrayInputStream generateRendezVousPdf(List<RendezVousDTO> rdvs) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            // Title
            document.add(new Paragraph("Liste des Rendez-vous"));
            document.add(new Paragraph(" ")); // space

            // Table
            PdfPTable table = new PdfPTable(3);

            // Header
            table.addCell("Date");
            table.addCell("Patient");
            table.addCell("Statut");

            // Data
            for (RendezVousDTO r : rdvs) {
                table.addCell(String.valueOf(r.getDateRendezVous()));
                table.addCell(String.valueOf(r.getIdPatient()));
                table.addCell(String.valueOf(r.getStatut()));
            }

            document.add(table);
            document.close();

            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }
}