package programming.task2;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public abstract class Base {

    static String[] fileSizeUnits = {"B", "KB", "MB", "GB"};
    public static String calculateFileSize(double bytes, int standard) {
        int index = 0;
        for (index = 0; index < fileSizeUnits.length; index++) {
            if (bytes < standard) {
                break;
            }
            bytes = bytes / standard;
        }

        Locale locale  = new Locale("en", "US");
        String pattern = "##.##";

        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);

        return decimalFormat.format(bytes) + " " + fileSizeUnits[index];
    }




    public static String calculateFullSize(File[] files) {
        double sumBytes = 0.0;
        for (File file : files) {
            sumBytes += file.length();
        }
        return calculateFileSize(sumBytes,1024);
    }
}
