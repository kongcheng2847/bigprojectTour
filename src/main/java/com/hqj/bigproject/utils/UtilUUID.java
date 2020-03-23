package com.hqj.bigproject.utils;

import java.util.Random;
import java.util.UUID;

public class UtilUUID {
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static String newUUID(int paramInt) {
        Random localRandom = new Random();
        char[] arrayOfChar = new char[paramInt];
        for (int i = 0; i < arrayOfChar.length; i++) {
            arrayOfChar[i] = digits[localRandom.nextInt(digits.length)];
        }
        return new String(arrayOfChar);
    }

    public static String newShortUUID() {
        return newUUID(16);
    }

    public static String randomUUID() {
        String str = UUID.randomUUID().toString();
        return str;
    }
}
