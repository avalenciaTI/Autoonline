package com.solera.global.qa.template.web.behavior.data.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Random;

public class IdGenerator {

    public static final String VIN_PREFIX = "1JKTS";
    private static final Random RANDOM = new Random();

    private static final String[] NOMBRES = {"Juan", "Maria", "Jose", "Ana", "Luis", "Carmen", "Pedro", "Lucia", "Carlos", "Sofia"};
    private static final String[] APELLIDOS = {"Hernandez", "Garcia", "Martinez", "Lopez", "Gonzalez", "Rodriguez", "Perez", "Sanchez", "Ramirez", "Flores"};
    public static final String[] ENTIDADES = {"AS", "BC", "BS", "CC", "CL", "CM", "CS", "CH", "DF", "DG", "GT", "GR", "HG", "JC", "MC", "MN", "MS", "NT", "NL", "OC", "PL", "QT", "QR", "SP", "SL", "SR", "TC", "TS", "TL", "VZ", "YN", "ZS"};

    private IdGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static String getNewVin() {
        return VIN_PREFIX + System.currentTimeMillis() / 10;
    }

    private static String removeAccents(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
    }

    public static String getNewRfc() {
        String nombre    = removeAccents(NOMBRES[RANDOM.nextInt(NOMBRES.length)]);
        String apellido1 = removeAccents(APELLIDOS[RANDOM.nextInt(APELLIDOS.length)]);
        String apellido2 = removeAccents(APELLIDOS[RANDOM.nextInt(APELLIDOS.length)]);

        // Primera vocal interna del apellido paterno
        char vocalAp1 = 'X';
        for (int i = 1; i < apellido1.length(); i++) {
            if ("AEIOU".indexOf(apellido1.charAt(i)) >= 0) {
                vocalAp1 = apellido1.charAt(i);
                break;
            }
        }

        // 4 letras: ap1[0] + vocal + ap2[0] + nombre[0]
        StringBuilder rfc = new StringBuilder();
        rfc.append(apellido1.charAt(0));
        rfc.append(vocalAp1);
        rfc.append(apellido2.charAt(0));
        rfc.append(nombre.charAt(0));

        // 6 dígitos fecha nacimiento AAMMDD
        int year  = 50 + RANDOM.nextInt(40);
        int month = 1  + RANDOM.nextInt(12);
        int day   = 1  + RANDOM.nextInt(28);
        rfc.append(String.format("%02d%02d%02d", year, month, day));

        // Homoclave: 2 letras + 1 dígito
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        rfc.append(letters.charAt(RANDOM.nextInt(letters.length())));
        rfc.append(letters.charAt(RANDOM.nextInt(letters.length())));
        rfc.append(RANDOM.nextInt(10));

        return rfc.toString();
    }

    public static String getNewPublicationId() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmss");
        var currentDate = Calendar.getInstance().getTime();
        return  "bulkaward-TestAutomation" + dateFormat.format(currentDate);
    }    

}
