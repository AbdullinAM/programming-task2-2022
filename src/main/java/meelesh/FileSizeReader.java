package meelesh;

import java.io.File;

public interface FileSizeReader {

    File getFileFromString(String filename);

    double getSizeBytes(File file);

}
