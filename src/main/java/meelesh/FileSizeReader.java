package meelesh;

import java.io.File;

public interface FileSizeReader {

    File getFileFromString(String filename);
    double getSizeBytes(File file);
    double convertBytesToKilobytes(double bits);
    double convertBytesToMegabytes(double bits);

}
