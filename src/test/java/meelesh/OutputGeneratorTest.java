package meelesh;

import meelesh.du.DuParametersDto;
import meelesh.du.OutputGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Main.class})
public class OutputGeneratorTest {

    private OutputGenerator outputGenerator;

    @Autowired
    public OutputGeneratorTest(OutputGenerator outputGenerator) {
        this.outputGenerator = outputGenerator;
    }

    @Test
    public void calculateTotalSizeTest() {
        assertEquals("Total size: 1.00", outputGenerator.calculateTotalSize(1024L));
        DuParametersDto.h = true;
        assertEquals("Total size: 1.00 KB", outputGenerator.calculateTotalSize(1024L));
    }

    @Test
    public void convertToReadableFormatTest() {
        DuParametersDto.si = true;
        assertEquals("10.10 KB", outputGenerator.convertToReadableFormat(10100L));
        DuParametersDto.si = false;
        assertEquals("1.95 MB", outputGenerator.convertToReadableFormat(2048576L));
        assertEquals("1.00 GB", outputGenerator.convertToReadableFormat(1073741824L));
    }

    @Test
    public void exploreFilesTest() {
        outputGenerator.getFiles().clear();
        String file1 = new File("").getAbsolutePath() + "/src/test/source/test/f.docx";
        String file2 = new File("").getAbsolutePath() + "/src/test/source/test/a/dsfas.pptx";
        String file3 = new File("").getAbsolutePath() + "/src/test/source/test/a/ggg.txt";
        outputGenerator.getFiles().add(file1);
        outputGenerator.getFiles().add(file2);
        outputGenerator.getFiles().add(file3);
        outputGenerator.exploreFiles();
        assertEquals(11185, outputGenerator.getFileAndBytes().get(file1));
        assertEquals(28966, outputGenerator.getFileAndBytes().get(file2));
        assertEquals(10900, outputGenerator.getFileAndBytes().get(file3));
    }

}
