package com.project.management.common.util.pdf;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfUtils {

    public static byte[] joinPdf(List<byte[]> pdfsBytes, String namePdf, String pathFont, boolean paginate) {
        // Primero se unen o mergean todos los pdfs
        byte[] mergedPdf = mergePdfs(pdfsBytes, namePdf);

        // Cuando ya tenemos todo mergeado entonces paginamos
        if (paginate) {
            mergedPdf = paginatePdf(mergedPdf, pathFont);
        }
        return mergedPdf;
    }

    private static byte[] mergePdfs(List<byte[]> pdfsBytes, String namePdf) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfDocument mergedPdf = new PdfDocument(new PdfWriter(outputStream));
            mergedPdf.getDocumentInfo().setTitle(namePdf);

            for (byte[] pdfBytes : pdfsBytes) {
                try (PdfDocument tempPdf = new PdfDocument(new PdfReader(new ByteArrayInputStream(pdfBytes)))) {
                    tempPdf.copyPagesTo(1, tempPdf.getNumberOfPages(), mergedPdf);
                }
            }

            mergedPdf.close();
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error al hacer join de PDF", e);
        }
    }

    public static byte[] paginatePdf(byte[] bytesPdf, String pathFont) {
        try (
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PdfDocument originalPdf = new PdfDocument(new PdfReader(new ByteArrayInputStream(bytesPdf)), new PdfWriter(outputStream))
        ) {
            originalPdf.getDocumentInfo().setTitle("Reporte de la terminal");
            PdfFont robotoFont = PdfFontFactory.createFont(pathFont);
            int numberOfPages = originalPdf.getNumberOfPages();

            for (int i = 1; i <= numberOfPages; i++) {
                addPageNumber(originalPdf.getPage(i), i, robotoFont);
            }

            originalPdf.close();
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error al paginar el PDF", e);
        }
    }

    private static void addPageNumber(PdfPage page, int pageNumber, PdfFont font) {
        PdfCanvas canvas = new PdfCanvas(page);
        Rectangle pageSize = page.getPageSize();

        float x = pageSize.getRight() - 50;
        float y = pageSize.getBottom() + 20;

        canvas.beginText()
                .setFontAndSize(font, 14)
                .setColor(ColorConstants.WHITE, true)
                .moveText(x, y)
                .showText(String.valueOf(pageNumber))
                .endText();
    }
}
