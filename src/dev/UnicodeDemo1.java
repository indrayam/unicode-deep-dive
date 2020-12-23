package dev;

public class UnicodeDemo1 {
    public static void main(String[] args) {
        // If the specified code point is part of the Unicode Basic Multilingual Plane (BMP)
        char[] name = {'\u0906', '\u0928', '\u0929', '\u0926'};
        System.out.printf("name in hindi (char array variable of escaped unicode character(s)): %s%n", String.valueOf(name));
        String name1 = "\u0906\u0928\u0929\u0926";
        System.out.printf("name in hindi (String variable of escaped unicode character(s)): %s%n", name1);
        String name2 = "आनंद";
        System.out.printf("name in hindi (String variable of actual unicode character(s), as typed in): %s%n", name2);
        System.out.println();

        // If the specified code point is a supplementary code point. Translation: Unicode character is NOT part of Basic Multilingual Plane (MBP)
        // Using the char[] or String statement approach shown above will not work
        char[] bike = Character.toChars(0x1F6B4);
        System.out.printf("bike emoji (char array variable of escaped unicode character(s)): %s%n", String.valueOf(bike));
        // If you want to assign it to a String, you can do it the way shown below
        String bike1 = String.valueOf(Character.toChars(0x1F6B4));
        System.out.printf("bike emoji (String variable of escaped unicode character(s)): %s%n", bike1);
        String bike2 = "🚴";
        System.out.printf("bike emoji (String variable of actual unicode character(s), as typed in): %s%n", bike2);
    }
}
