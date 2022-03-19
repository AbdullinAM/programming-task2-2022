package meelesh;

import java.io.File;

public class FIleSizeReaderImpl implements FileSizeReader {

    public File getFileFromString(String filename) { return new File(filename); }

    public double getSizeBytes(File file) {
        return (double) file.length();
    }
}
