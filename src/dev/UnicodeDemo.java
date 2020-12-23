package dev;

public class UnicodeDemo {

    public static void main(String[] args) {
        char ch1 = '\u0041';
        char ch2 = '\u005a';
        char ch3 = '\u0061';
        char ch4 = '\u007a';
        char ch5 = '\u005f';
        char ch6 = '\u0024';
        char ch7 = '\u0030';
        char ch8 = '\u0039';
        char ch9 = '\u2764';
        char ch10 = '\u2122';
        char ch11 = '\u03C0';
        char ch12 = '\u005B';
        char ch13 = '\u005D';
        char ch14 = '\u0020';
        System.out.printf("ch1: |%s|%n", ch1);
        System.out.printf("ch2: |%s|%n", ch2);
        System.out.printf("ch3: |%s|%n", ch3);
        System.out.printf("ch4: |%s|%n", ch4);
        System.out.printf("ch5: |%s|%n", ch5);
        System.out.printf("ch6: |%s|%n", ch6);
        System.out.printf("ch7: |%s|%n", ch7);
        System.out.printf("ch8: |%s|%n", ch8);
        System.out.printf("ch9: |%s|%n", ch9);
        System.out.printf("ch10: |%s|%n", ch10);
        System.out.printf("ch11: |%s|%n", ch11);
        System.out.printf("ch12: |%s|%n", ch12);
        System.out.printf("ch13: |%s|%n", ch13);
        System.out.printf("ch14: |%s|%n", ch14);
        System.out.println();

        // This approach only works if the Unicode is in BMP (basic multilingual plane)
        int c = 0x2202;
        char ch = (char) c;
        System.out.printf("Character using Unicode int value: %s%n", ch);
        System.out.println();

        // This approach also only works if the Unicode is in BMP (basic multilingual plane)
        String cc2 = "2202";
        String text2 = String.valueOf(Character.toChars(Integer.parseInt(cc2, 16)));
        System.out.printf("Character using Unicode int value: %s%n", text2);
        System.out.println();

        // This approach will always work, especially for Unicode characters that are not in BPM. However, you do
        // need to know the code unit values
        char sup1 = '\uD835';
        char sup2 = '\uDD46';
        System.out.printf("sup1 using two strings in output: %s%s%n", sup1, sup2);
        System.out.println();

        // This approach will always work, especially for Unicode characters that are not in BPM. However, you do
        // need to know the code unit values
        char sup3 = '\u2764';
        char sup4 = '\ufe0f';
        System.out.printf("sup2 using two strings in output: %s%s%n", sup3, sup4);
        System.out.println();

        // This approach will always work, especially for Unicode characters that are not in BPM. However, you do
        // need to know the code unit values. What's cool here is that you can get a String value out of it easily.
        char[] sup5 = {'\u2764', '\ufe0f'};
        System.out.printf("sup5 using 2 code units: : %s%n", String.valueOf(sup5));
        System.out.println();

        // This approach will always work. In this case, you just need to know the Code point, not the individual code
        // units. In this case, you do not care if it is in BMP or not.
        char[] sup6 = Character.toChars(0x1D546);
        System.out.printf("sup6 code unit 1: %x%n", (int) sup6[0]);
        System.out.printf("sup6 code unit 2: %x%n", (int) sup6[1]);
        System.out.printf("sup6 using code point: %s%n", String.valueOf(sup6));
        System.out.println();

        // This approach will always work. In this case, you just need to know the Code point, not the individual code
        // units. In this case, you do not care if it is in BMP or not. However this approach allows you to know if
        // there is a supplementary character
        char[] sup7 = {' ', ' '};
        int val = Character.toChars(0x09B2, sup7, 0);
        System.out.printf("sup7 code unit 1: %x%n", (int) sup7[0]);
        System.out.printf("sup7 code unit 2: %x%n", (int) sup7[1]);
        System.out.printf("sup7 has %d code units. code unit 1 = %x, code unit 2 = %x%n", val,
                Character.codePointAt(sup7, 0),
                Character.codePointAt(sup7, 1));
        System.out.printf("sup7 using code point: %s%n", String.valueOf(sup7));

        // This approach will always work. In this case, you just need to know the Code point, not the individual code
        // units. In this case, you do not care if it is in BMP or not. However this approach allows you to know if
        // there is a supplementary character
        char[] sup8 = {' ', ' '};
        int val1 = Character.toChars(0x1F600, sup8, 0);
        System.out.println();
        System.out.printf("sup8 has %d code units. code unit 1 = %x, code unit 2 = %x%n", val1,
                Character.codePointAt(sup8, 0),
                Character.codePointAt(sup8, 1));
        System.out.printf("sup8 using code point: %s%n", String.valueOf(sup8));
        System.out.println();

        // char[] sup9 = Character.toChars(Character.codePointOf("FACE WITH COWBOY HAT"));
        char[] sup9 = Character.toChars(Character.codePointOf("FACE SAVOURING DELICIOUS FOOD"));
        System.out.printf("sup9 using Unicode name: %s%n", String.valueOf(sup9));
        System.out.println();

        char[] aa = Character.toChars(0x0906);
        char[] na = Character.toChars(0x0928);
        char[] n = Character.toChars(0x0929);
        char[] d = Character.toChars(0x0926);
        char[] name = {'\u0906', '\u0928', '\u0929', '\u0926'};
        System.out.printf("%s%s%s%s%n", String.valueOf(aa), String.valueOf(na), String.valueOf(n), String.valueOf(d));
        System.out.printf("%s%n", String.valueOf(name));


    }

}
