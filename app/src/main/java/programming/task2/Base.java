package programming.task2;

import java.io.File;


public class Base {

    static String[] fileSizeUnits = {"B", "KB", "MB", "GB"};
    public static String calculateFileSize(double bytes, int standard) {
        int index = 0;
        for (index = 0; index < fileSizeUnits.length; index++) {
            if (bytes < standard) {
                break;
            }
            bytes = bytes / standard;
        }
        return String.format("%.2f", bytes) + " " + fileSizeUnits[index];
    }

    public static String calculateFullSize(File[] files) {
        double sumBytes = 0.0;
        for (File file : files) {
            sumBytes += file.length();
        }
        return calculateFileSize(sumBytes,1024);
    }
}
