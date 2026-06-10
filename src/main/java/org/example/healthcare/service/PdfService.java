package org.example.healthcare.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.example.healthcare.dto.RendezVousDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generateRendezVousPdf(List<RendezVousDTO> rdvs) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("Liste des Rendez-vous du patient"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);

            table.addCell("Date");
            table.addCell("Patient");
            table.addCell("Statut");

            for (RendezVousDTO r : rdvs) {
                table.addCell(String.valueOf(r.getDateRendezVous()));
                table.addCell(String.valueOf(r.getIdPatient()));
                table.addCell(String.valueOf(r.getStatut()));
            }

            document.add(table);
            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}