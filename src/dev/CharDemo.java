package dev;

public class CharDemo {
    public static void main(String[] args) {
        char ch1, ch2;

        // Version 1: Using decimal integer values as a char
        ch1 = 88;
        ch2 = 89;
        System.out.printf("ch1 and ch2 are set to %c (0x%x) and %c (0x%x) respectively%n",
                ch1,
                (int) ch1,
                ch2,
                (int) ch2);

        // Version 2: Using hexadecimal values of char as a char
        ch1 = '\u0058';
        ch2 = '\u0059';
        System.out.printf("ch1 and ch2 are set to %c (0x%x) and %c (0x%x) respectively%n",
                ch1,
                (int) ch1,
                ch2,
                (int) ch2);

        // Version 3: Using hexadecimal values as ints
        int ch3 = 0x0058;
        int ch4 = 0x0059;
        System.out.printf("ch3 and ch4 are set to %c (0x%x) and %c (0x%x) respectively%n",
                ch3,
                (int) ch3,
                ch4,
                (int) ch4);
    }
}

