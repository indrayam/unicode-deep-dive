package dev;

public class UnicodeDemo2 {
    public static void main(String[] args) {
        String greeting = "H\uD83D\uDE1E\uD83C\uDF7Aello";
        char ch = greeting.charAt(0);
        System.out.printf("Greeting = %s%n", greeting);
        System.out.println("Greeting length (number of code units used) = " + greeting.length());
        int cpCount = greeting.codePointCount(0, greeting.length());
        System.out.println("Greeting length (number of Code Points) = " + cpCount);
        System.out.printf("Character at %d = %c%n", 0, ch);
        ch = greeting.charAt(1);
        System.out.printf("Character at %d = %c%n", 1, ch);
        int cp;
        String[] coolStrings = new String[2];
        int coolStrCount = 0;
        for (int i = 0; i < greeting.length(); i++) {
            cp = greeting.codePointAt(i);
            if (Character.isSupplementaryCodePoint(cp)) {
                System.out.printf("Supplementary Code Point found and Character = %c%n", cp);
                coolStrings[coolStrCount] = new StringBuilder().appendCodePoint(cp).toString();
                coolStrCount++;
                i++;
            } else if (Character.isSurrogate(greeting.charAt(i))) {
                System.out.printf("%d (0x%<x) is a Surrogate Code Point%n", cp);
            } else {
                System.out.printf("%d (0x%<x) is a \"classic\" Unicode Character = %<c%n", cp);
            }
        }
        System.out.println("First Cool String is " + coolStrings[0]);
        System.out.println("Second Cool String is " + coolStrings[1]);
        System.out.println("-".repeat(10));
        for (int i = 0; i < greeting.length(); i++) {
            cp = greeting.codePointAt(i);
            if (Character.isSupplementaryCodePoint(cp)) {
                System.out.printf("Supplementary Code Point found and Character = %c%n", cp);
            } else if (Character.isSurrogate(greeting.charAt(i))) {
                System.out.printf("%d (0x%<x) is a Surrogate Code Point%n", cp);
            } else {
                System.out.printf("%d (0x%<x) is a \"classic\" Unicode Character = %<c%n", cp);
            }
        }
    }
}
