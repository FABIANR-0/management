package com.project.management.common.util.pdf;

import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class GeneratePdfService {



    /**
     * Genera un documento PDF a partir de una plantilla HTML y devuelve el PDF resultante como una cadena codificada en Base64.
     * Utiliza la clase ITextRenderer de la biblioteca iText para renderizar el PDF y maneja la conversión y codificación del contenido del PDF.
     *
     * @param template La cadena de plantilla HTML que se convertirá en un documento PDF.
     * @return La cadena codificada en Base64 que representa el documento PDF generado.
     * @throws RuntimeException Sí ocurre una IOException durante el proceso de generación del PDF.
     */
    public String generatePdfFromHtmlTemplate(String template, String letterheadName) {
        // Usar un bloque try-with-resources para asegurar que el ByteArrayOutputStream se cierre correctamente.
        try (ByteArrayOutputStream htmlOutputStream = new ByteArrayOutputStream()) {

            // Renderizar HTML en PDF
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(template);
            renderer.layout();
            renderer.createPDF(htmlOutputStream);
            renderer.finishPDF();

            // Convertir el contenido del outputStream en un arreglo de bytes.
            byte[] generatedPdfBytes = htmlOutputStream.toByteArray();

            // Convertir a Base64 directamente desde el byte[]
            return Base64.getEncoder().encodeToString(generatedPdfBytes);
        } catch (IOException | DocumentException e) {
            throw new RuntimeException("Error al generar el PDF con marca de agua", e);
        }
    }


}
