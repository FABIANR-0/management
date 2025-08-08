package com.project.management.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.zip.GZIPInputStream;

@Slf4j
public class UtilFile {

    /**
     * Calcula el hash SHA-1 de un array de bytes dado y lo devuelve como una cadena hexadecimal.
     *
     * @param convert El array de bytes para calcular su hash SHA-1.
     * @return El hash SHA-1 del array de bytes como una cadena hexadecimal.
     */
    public static String SHAsum(byte[] convert) {
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            hash = byteArray2Hex(md.digest(convert));
        } catch (Exception ex) {
            log.error("Error al generar el hash de la firma [{}]", ex.getMessage());
        }
        return hash;
    }

    /**
     * Convierte un array de bytes en una cadena hexadecimal.
     * Cada byte del array se convierte en dos caracteres hexadecimales.
     *
     * @param hash El array de bytes a convertir.
     * @return Una cadena hexadecimal representando el array de bytes, en mayúsculas.
     */
    private static String byteArray2Hex(final byte[] hash) {
        String output = "";
        try {
            Formatter formatter = new Formatter();
            for (byte b : hash) {
                formatter.format("%02x", b);
            }
            output = formatter.toString().toUpperCase();
        } catch (Exception ex) {
            log.error("Error al generar la cadena hexadecimal del hash [{}]", ex.getMessage());
        }
        return output;
    }

    public static byte[] decompress(byte[] compressedData) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPInputStream gzipIS = new GZIPInputStream(bis);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gzipIS.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            gzipIS.close();
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("RECHAZADO - ERROR DESCOMPRIMIENDO : " + " [" + e.getMessage() + "]", "01", e);
            return new byte[0];
        }
    }

    /**
     * Converts a byte array to an integer.
     * This method pads the input byte array to 4 bytes if necessary and interprets it as a big-endian integer.
     * If the input is null or empty, it returns 0.
     *
     * @param bytes The byte array to be converted to an integer. Can be null or empty.
     * @return The integer value represented by the byte array. Returns 0 if the input is null or empty.
     */
    public static int byte2int(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return 0;
        }

        // Crear un arreglo de 4 bytes, inicializado con ceros
        byte[] paddedBytes = new byte[4];

        // Copiar los bytes originales al final del arreglo paddedBytes
        System.arraycopy(bytes, 0, paddedBytes, 4 - bytes.length, bytes.length);

        // Convertir a entero con ByteBuffer
        return ByteBuffer.wrap(paddedBytes).getInt();
    }

}
