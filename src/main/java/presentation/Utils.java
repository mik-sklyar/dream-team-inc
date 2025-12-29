package presentation;

public class Utils {
    public static String veryWhiteString(String value) {
        return "\u001B[97m" + value + "\u001B[0m";
    }

    public static String veryLightBlueString(String value) {
        return "\u001B[94m" + value + "\u001B[0m";
    }

    public static void drawTree() {
        String[] a = {
                "\u001B[32m.\u001B[0m",
                "\u001B[32m~\u001B[0m",
                "\u001B[34m'\u001B[0m",
                "\u001B[33mO\u001B[0m",
                "\u001B[34m'\u001B[0m",
                "\u001B[32m~\u001B[0m",
                "\u001B[32m.\u001B[0m",
                "\u001B[31m*\u001B[0m"};

        int s = 17, w = 1, r = 7;
        for (int n = 1; n <= s; n++) {
            for (int i = 0; i < s - n; i++) {
                System.out.print(" ");
            }
            for (int i = 0; i < w; i++) {
                System.out.print(a[r]);
                r++; if (r > 6) r = 0;
            }
            w += 2;
            System.out.println();
        }
        System.out.println();
    }
}
