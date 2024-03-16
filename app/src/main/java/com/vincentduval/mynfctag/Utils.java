package com.vincentduval.mynfctag;

public class Utils {

    public static String convertBytesToStringHexa(byte[] bytes) {
        if (bytes == null) {return null;}
        if (bytes.length == 0) {return "";}

        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : bytes) {
            int dec = (int) b; if (dec < 0) {dec = dec + 256;}
            int dec2 = dec % 16; int dec1 = (dec - dec2) / 16;
            stringBuilder.append(convertDecToChar(dec1)); stringBuilder.append(convertDecToChar(dec2)); stringBuilder.append(" ");
        }

        String stringHexa = stringBuilder.toString();
        stringHexa = stringHexa.substring(0, stringHexa.length() - 1);

        return stringHexa;
    }
    private static char convertDecToChar(int dec) {
        if (dec >= 0 && dec <= 9) {char c = (char) (48+dec); return c;}
        if (dec >= 10 && dec <= 15) {char c = (char) (dec-10+97); return c;}
        return '?';
    }

    public static String convertBytesToStringHexaReadable(byte[] bytes) {
        if (bytes == null) {return null;}
        if (bytes.length == 0) {return "";}

        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : bytes) {
            int dec = (int) b;
            if ((dec >= 46 && dec <= 57) || (dec >= 65 && dec <= 90) || (dec >= 97 && dec <= 122)) {
                stringBuilder.append((char) dec);
            } else {
                stringBuilder.append(String.format("%02X", b));
            }
            stringBuilder.append(" ");}

        String stringHexaReadable = stringBuilder.toString();
        stringHexaReadable = stringHexaReadable.substring(0, stringHexaReadable.length() - 1);

        return stringHexaReadable;
    }


    public static byte[] convertStringHexaToBytes(String stringHexa) {
        String string = stringHexa.replace(" ", "").toLowerCase();
        if (string.length() % 2 == 1) {return null;}

        byte[] bytes = new byte[string.length()/2];

        for (int i = 0; i < string.length()/2; i++) {bytes[i] = (byte) (convertChartoSemiByte(string.charAt(2*i)) * 16 + convertChartoSemiByte(string.charAt(2*i+1)));}

        return bytes;
    }


    private static int convertChartoSemiByte(char c) {
        int n = (int) c;
        if (n >= 48 && n <= 57) {return n-48;}
        if (n >= 97 && n <= 102) {return n-97+10;}
        return -999;
    }

}
