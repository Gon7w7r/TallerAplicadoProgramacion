package com.sistema.sistematomahorarios.services;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sistema.sistematomahorarios.dto.InscripcionDetalleDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PdfHorarioService {

    private static final int[][] COLORES_RAMOS = {
        {0xDBEAFE, 0x1E40AF},
        {0xD1FAE5, 0x065F46},
        {0xFEF3C7, 0x92400E},
        {0xEDE9FE, 0x5B21B6},
        {0xFCE7F3, 0x9D174D},
        {0xCFFAFE, 0x155E75},
    };

    private static final String[] DIAS = {
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"
    };

    private static final BaseColor COLOR_HEADER   = new BaseColor(0x1A, 0x2E, 0x4A);
    private static final BaseColor COLOR_HORA_BG  = new BaseColor(0xF9, 0xFA, 0xFB);
    private static final BaseColor COLOR_HORA_TEXT = new BaseColor(0x37, 0x41, 0x51);
    private static final BaseColor COLOR_BORDE    = new BaseColor(0xE5, 0xE7, 0xEB);
    private static final int CELL_HEIGHT = 50;

    public byte[] generarPdf(String nombreAlumno, List<InscripcionDetalleDTO> inscripciones) throws Exception {

        Map<String, Map<String, InscripcionDetalleDTO>> grid = new LinkedHashMap<>();

        for (InscripcionDetalleDTO ins : inscripciones) {
            grid.computeIfAbsent(ins.getDiaSemana(), k -> new HashMap<>())
                .put(ins.getHoraInicio(), ins);
        }

        List<String> horas = generarHoras(inscripciones);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4.rotate(), 20, 20, 30, 20);
        PdfWriter.getInstance(doc, out);
        doc.open();

        Font fTitulo    = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, COLOR_HEADER);
        Font fSubtitulo = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL, new BaseColor(100, 100, 100));
        doc.add(new Paragraph("Nexus Materia — Horario Semanal", fTitulo));
        doc.add(new Paragraph("Alumno: " + nombreAlumno, fSubtitulo));
        doc.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{2f, 2.5f, 2.5f, 2.5f, 2.5f, 2.5f, 2.5f});
        tabla.setSpacingBefore(6f);

        Font fHeader = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.WHITE);
        tabla.addCell(headerCell("Hora", fHeader));
        for (String dia : DIAS) {
            tabla.addCell(headerCell(dia, fHeader));
        }

        Set<String> ocupadas = new HashSet<>();

        for (int rowIndex = 0; rowIndex < horas.size(); rowIndex++) {
            String hora = horas.get(rowIndex);

            PdfPCell celdaHora = new PdfPCell(new Phrase(hora,
                new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, COLOR_HORA_TEXT)));
            celdaHora.setBackgroundColor(COLOR_HORA_BG);
            celdaHora.setBorderColor(COLOR_BORDE);
            celdaHora.setPadding(6);
            celdaHora.setFixedHeight(CELL_HEIGHT);
            tabla.addCell(celdaHora);

            for (String dia : DIAS) {
                String key = dia + "-" + rowIndex;

                if (ocupadas.contains(key)) continue;

                InscripcionDetalleDTO bloque = grid
                    .getOrDefault(dia, Collections.emptyMap())
                    .get(hora);

                if (bloque == null) {
                    PdfPCell vacia = new PdfPCell();
                    vacia.setBorderColor(COLOR_BORDE);
                    vacia.setFixedHeight(CELL_HEIGHT);
                    tabla.addCell(vacia);
                } else {
                    int span = calcularSpan(bloque.getHoraInicio(), bloque.getHoraFin());

                    for (int s = 1; s < span; s++) {
                        ocupadas.add(dia + "-" + (rowIndex + s));
                    }

                    int[] colores   = COLORES_RAMOS[bloque.getIdAsignatura() % COLORES_RAMOS.length];
                    BaseColor bgColor  = new BaseColor((colores[0] >> 16) & 0xFF, (colores[0] >> 8) & 0xFF, colores[0] & 0xFF);
                    BaseColor txtColor = new BaseColor((colores[1] >> 16) & 0xFF, (colores[1] >> 8) & 0xFF, colores[1] & 0xFF);

                    Font fRamo    = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD,   txtColor);
                    Font fDetalle = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, txtColor);

                    Paragraph contenido = new Paragraph();
                    contenido.add(new Chunk(bloque.getNombreAsignatura() + "\n", fRamo));
                    contenido.add(new Chunk("Sección " + bloque.getIdSeccion() + "\n", fDetalle));
                    contenido.add(new Chunk(bloque.getHoraInicio() + " - " + bloque.getHoraFin() + "\n", fDetalle));
                    contenido.add(new Chunk(bloque.getSala(), fDetalle));

                    PdfPCell celdaRamo = new PdfPCell(contenido);
                    celdaRamo.setRowspan(span);
                    celdaRamo.setBackgroundColor(bgColor);
                    celdaRamo.setBorderColor(COLOR_BORDE);
                    celdaRamo.setPadding(5);
                    celdaRamo.setFixedHeight(CELL_HEIGHT * span);
                    celdaRamo.setVerticalAlignment(Element.ALIGN_TOP);
                    tabla.addCell(celdaRamo);
                }
            }
        }

        doc.add(tabla);
        doc.close();
        return out.toByteArray();
    }

    private List<String> generarHoras(List<InscripcionDetalleDTO> inscripciones) {
        int minMin = Integer.MAX_VALUE;
        int maxMin = Integer.MIN_VALUE;

        for (InscripcionDetalleDTO ins : inscripciones) {
            int[] ini = parsearHora(ins.getHoraInicio());
            int[] fin = parsearHora(ins.getHoraFin());
            minMin = Math.min(minMin, ini[0] * 60 + ini[1]);
            maxMin = Math.max(maxMin, fin[0] * 60 + fin[1]);
        }

        List<String> horas = new ArrayList<>();
        for (int m = minMin; m < maxMin; m += 60) {
            horas.add(String.format("%02d:%02d", m / 60, m % 60));
        }
        return horas;
    }

    private int calcularSpan(String horaInicio, String horaFin) {
        int[] ini = parsearHora(horaInicio);
        int[] fin = parsearHora(horaFin);
        int diffMin = (fin[0] * 60 + fin[1]) - (ini[0] * 60 + ini[1]);
        return Math.max(1, diffMin / 60);
    }

    private int[] parsearHora(String hora) {
        String[] partes = hora.split(":");
        return new int[]{Integer.parseInt(partes[0]), Integer.parseInt(partes[1])};
    }

    private PdfPCell headerCell(String texto, Font fuente) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, fuente));
        cell.setBackgroundColor(COLOR_HEADER);
        cell.setBorderColor(COLOR_BORDE);
        cell.setPadding(8);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(35);
        return cell;
    }
}