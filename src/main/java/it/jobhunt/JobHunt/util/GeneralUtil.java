package it.jobhunt.JobHunt.util;

import java.util.Collection;
import java.util.Objects;

public class GeneralUtil {

    public static boolean isNullOrEmpty(Collection collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(String str){
        return str == null || str.equals("");
    }

    public static boolean isNull(Object classes){
        return Objects.isNull(classes);
    }


    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (padStr == null || padStr.length() == 0) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return leftPad(str, size, String.valueOf(padStr.charAt(0)));
            } else if (pads == padLen) {
                return padStr.concat(str);
            } else if (pads < padLen) {
                return padStr.substring(0, pads).concat(str);
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }
}
