package meelesh.du;

import java.io.File;

public interface FileSizeReader {

    File getFileFromString(String filename);

    Long getSizeBytes(File file);

}
