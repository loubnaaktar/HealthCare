package org.example.healthcare.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.example.healthcare.model.DossierMedical;
import org.example.healthcare.model.RendezVous;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] generateRendezVousPdf(List<RendezVous> rdvs) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("Liste des Rendez-vous"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);

            table.addCell("Date");
            table.addCell("Patient");
            table.addCell("Medecin");
            table.addCell("Statut");

            for (RendezVous r : rdvs) {
                table.addCell(String.valueOf(r.getDateRendezVous()));
                table.addCell(r.getPatient().getNom() + " " + r.getPatient().getPrenom());
                table.addCell(r.getMedecin().getNom());
                table.addCell(String.valueOf(r.getStatut()));
            }

            document.add(table);
            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateDossierMedicalPdf(DossierMedical dm) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Dossier Medical"));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Patient: " + dm.getPatient().getNom() + " " + dm.getPatient().getPrenom()));
            document.add(new Paragraph("Date de naissance: " + dm.getPatient().getDateNaissance()));
            document.add(new Paragraph("Telephone: " + dm.getPatient().getTelephone()));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Diagnostic:"));
            document.add(new Paragraph(dm.getDiagnostic() != null ? dm.getDiagnostic() : "Non renseigné"));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Observation:"));
            document.add(new Paragraph(dm.getObservation() != null ? dm.getObservation() : "Non renseignée"));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Date de creation: " + dm.getDateCreation()));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] generateStatsPdf(long patientCount, long medecinCount, long rdvCount) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            document.add(new Paragraph("Rapport Statistique Global"));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Nombre total de patients : " + patientCount));
            document.add(new Paragraph("Nombre total de médecins : " + medecinCount));
            document.add(new Paragraph("Nombre total de rendez-vous : " + rdvCount));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("Date du rapport : " + java.time.LocalDate.now()));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}