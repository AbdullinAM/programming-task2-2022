package meelesh;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputGeneratorTest {

    @Test
    public void calculateTotalSizeTest() {
        ParseArgs parseArgs = new ParseArgs();
        OutputGenerator outputGenerator = new OutputGenerator();
        outputGenerator.setParseArgs(parseArgs);
        assertEquals("Total size: 1.00", outputGenerator.calculateTotalSize(1024));
        parseArgs.setH(true);
        assertEquals("Total size: 1.00 KB", outputGenerator.calculateTotalSize(1024));
    }

    @Test
    public void convertToReadableFormatTest() {
        OutputGenerator outputGenerator = new OutputGenerator();
        assertEquals("10.10 KB", outputGenerator.convertToReadableFormat(10100.0, 1000));
        assertEquals("1.95 MB", outputGenerator.convertToReadableFormat(2048576.0, 1024));
        assertEquals("1.00 GB", outputGenerator.convertToReadableFormat(1073741824.0, 1024));
    }

}
