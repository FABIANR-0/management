package com.project.management.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Clase de utilidad para manipulación de cadenas de texto.
 */
@Slf4j
public class UtilString {

    private static final Map<Character, Character> DIACRITIC_MAP = new HashMap<>();

    static {
        DIACRITIC_MAP.put('á', 'a');
        DIACRITIC_MAP.put('é', 'e');
        DIACRITIC_MAP.put('í', 'i');
        DIACRITIC_MAP.put('ó', 'o');
        DIACRITIC_MAP.put('ú', 'u');
        DIACRITIC_MAP.put('Á', 'A');
        DIACRITIC_MAP.put('É', 'E');
        DIACRITIC_MAP.put('Í', 'I');
        DIACRITIC_MAP.put('Ó', 'O');
        DIACRITIC_MAP.put('Ú', 'U');
        DIACRITIC_MAP.put('ü', 'u');
        DIACRITIC_MAP.put('Ü', 'U');
        DIACRITIC_MAP.put('ñ', 'n');
        DIACRITIC_MAP.put('Ñ', 'N');
    }

    /**
     * Genera un string con caracteres aleatorios
     *
     * @param size tamaño que tendrá la cadena
     * @return cadena de valores aleatorios
     */
    public static String generateRandom(Integer size) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#%&*()_+{}.";
        return RandomStringUtils.random(size, 0, 0, true, true, characters.toCharArray(), new SecureRandom());
    }

    /**
     * Convierte el nombre de un campo en un formato de nombre de campo válido.
     *
     * @param field El nombre del campo a convertir.
     * @return El nombre del campo convertido.
     */
    public static String fieldConvertName(String field) {
        String[] fieldSplit = field.split("(?<=.)(?=\\p{Lu})");
        StringBuilder fieldConverted = new StringBuilder();
        for (String key : fieldSplit) {
            fieldConverted.append(key).append("_");
        }
        return fieldConverted.toString().replaceFirst(".$", "").toLowerCase(Locale.ROOT);
    }

    /**
     * Genera una clave secreta aleatoria.
     *
     * @return La clave secreta generada.
     */
    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    /**
     * Convierte parte del correo electrónico enmascarando caracteres.
     *
     * @param email El correo electrónico a enmascarar.
     * @return El correo electrónico enmascarado.
     */
    public static String emailConvertMask(String email) {
        int centerCharacter = email.indexOf("@");
        int startCharacter = centerCharacter / 2;
        for (int i = startCharacter; i <= centerCharacter; i++) {
            email = email.substring(0, i - 1) + "*" + email.substring(i);
        }
        return email;
    }

    /**
     * Remueve un número específico de caracteres desde el inicio de una cadena.
     *
     * @param input        La cadena de entrada.
     * @param numberSpaces El número de caracteres a remover desde el inicio de la cadena.
     * @return La cadena resultante después de remover los caracteres especificados, o la cadena original si su longitud es menor o igual al número de caracteres especificado.
     */
    public static String removeLeftDigits(String input, int numberSpaces) {
        // Verificar si la longitud de la cadena es mayor que numberSpaces
        if (input.length() > numberSpaces) {
            //obtener los ultimos 10 caracteres de la cadena
            return input.substring(input.length() - numberSpaces);
        }
        //Devuelve la cadena original si la longitud no es mayor a la especificada
        return input;
    }

    public static String convertHexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }

    /**
     * Convierte una cadena de texto en su representación MD5.
     *
     * @param input La cadena de texto que se va a convertir.
     * @return La representación MD5 de la cadena de texto como una cadena hexadecimal.
     * Si ocurre algún error durante la conversión, se devuelve null.
     */
    public static String convertToMD5(String input) {
        try {
            // Crear un objeto MessageDigest con el algoritmo MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Convertir la cadena a bytes y calcular el hash
            byte[] mdBytes = md.digest(input.getBytes());
            // Convertir los bytes a formato hexadecimal
            StringBuilder sb = new StringBuilder();
            for (byte mdByte : mdBytes) {
                sb.append(Integer.toString((mdByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception ex) {
            log.error("Error al convertir la clave md5 [{}]", ex.getMessage());
            return null;
        }
    }

    public static String normalizeAndReplaceDiacritics(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            sb.append(DIACRITIC_MAP.getOrDefault(c, c));
        }
        return sb.toString().toLowerCase(Locale.ROOT);
    }

    public static String replaceCharacters(String value){
        return value.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#39;")
                .replaceAll("-", "&#45;")
                .replaceAll("\\\\", "&#92;")
                .replaceAll("/", "&#47;")
                .replaceAll("%", "&#37;")
                .replaceAll("\\$", "&#36;")
                .replaceAll("\\*", "&#42;")
                .replaceAll("\\+", "&#43;")
                .replaceAll("=", "&#61;")
                .replaceAll("\\?", "&#63;")
                .replaceAll("@", "&#64;")
                .replaceAll("\\[", "&#91;")
                .replaceAll("\\]", "&#93;")
                .replaceAll("\\{", "&#123;")
                .replaceAll("\\}", "&#125;")
                .replaceAll("~", "&#126;")
                .replaceAll("\\(", "&#40;")
                .replaceAll("\\)", "&#41;")
                .replaceAll("_", "&#95;");
    }
}
