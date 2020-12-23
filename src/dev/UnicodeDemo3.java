package dev;

public class UnicodeDemo3 {

    public static void main(String[] args) {
        char utfChar = 0x0041;
        System.out.println(utfChar);
        System.out.println(String.format("%s", String.valueOf(Character.toChars(0x1F496))));
        char[] heart = Character.toChars(0x1F496);
        System.out.println(heart.length);
        System.out.printf("%x%n", (int) heart[0]);
        System.out.printf("%x%n", (int) heart[1]);
        System.out.printf("%s%n", String.valueOf(heart));
        char[] a = Character.toChars(utfChar);
        System.out.println(a.length);
        int b1 = 0b01100010;
        int b2 = 0b01101001;
        int b3 = 0b01110100;
        int b4 = 0b01110011;
        System.out.printf("%d|%d|%d|%d%n", b1, b2, b3, b4);
        System.out.printf("%x|%x|%x|%x%n", b1, b2, b3, b4);
        System.out.printf("%c%c%c%c%n", b1, b2, b3, b4);
        String poo ="\uD83D\uDCA9";
        System.out.printf("%s%n", poo);
        String pinkHeart = "\uD83D\uDC96";
        System.out.printf("Pink Heart: %s%n", pinkHeart);
        System.out.printf("Length of Pink Heart: %d%n",pinkHeart.length());
        System.out.printf("Number of Code Points in Pink Heart: %d%n", pinkHeart.codePointCount(0, pinkHeart.length()));
        int cp;
        for (int i = 0; i < pinkHeart.length(); i++) {
            cp = pinkHeart.codePointAt(i);
            if (Character.isSupplementaryCodePoint(cp) && (Character.isSurrogate(pinkHeart.charAt(i)))) {
                System.out.printf("Supplementary Code Point: YES | Value: %c%n", cp);
                System.out.printf("High Surrogate: YES | Value: \\u%X%n", (int) pinkHeart.charAt(i));
            } else if (Character.isSurrogate(pinkHeart.charAt(i))) {
                System.out.printf("Low Surrogate: YES | Value: \\u%X%n", (int) pinkHeart.charAt(i));
            }
        }
    }
}
