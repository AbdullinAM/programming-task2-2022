package meelesh;

import meelesh.du.FileSizeReader;
import meelesh.du.SimpleFIleSizeReader;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FIleSizeReaderTest {

    @Test
    public void getSizeBytesTest() {
        FileSizeReader fileSizeReader = new SimpleFIleSizeReader();
        File file = fileSizeReader.getFileFromString(new File("").getAbsolutePath() + "/src/test/source/test/a/dsfas.pptx");
        File file1 = fileSizeReader.getFileFromString(new File("").getAbsolutePath() + "/src/test/source/test/f.docx");
        assertEquals(28966.0, fileSizeReader.getSizeBytes(file), 0.01);
        assertEquals(11185.0, fileSizeReader.getSizeBytes(file1), 0.01);
    }

}
