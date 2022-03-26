package meelesh.du;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class SimpleFIleSizeReader implements FileSizeReader {

    public File getFileFromString(String filename) { return new File(filename); }

    public Long getSizeBytes(File file) {
        return file.length();
    }
}
