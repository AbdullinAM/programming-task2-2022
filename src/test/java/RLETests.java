import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class RLETests {
    @Test
    public void packagerTest() {
        RLE RLE = new RLE();
        String packagerString1 = RLE.packager("IIIIIIIIIIIPPPPPPPPP YOPPPP VVVVVQQQQQQTTTTTTEEEEEEEEE");
        assertEquals("11I9P YO4P 5V6Q6T9E", packagerString1);

        String packagerString2 = RLE.packager("UUUUUIIIIOOOP HYU JJJJJGGCCCC OOOOP");
        assertEquals("5U4I3OP HYU 5J2G4C 4OP", packagerString2);
    }

    @Test
    public void unpackagerTest() {
        RLE RLE = new RLE();
        String unpackagerString1 = RLE.unpackager("5U8IH IOP 8N");
        assertEquals("UUUUUIIIIIIIIH IOP NNNNNNNN", unpackagerString1);

        String unpackagerString2 = RLE.unpackager("13E4G UY 2F");
        assertEquals("EEEEEEEEEEEEEGGGG UY FF", unpackagerString2);

        String unpackagerString3 = RLE.unpackager("z15x");
        assertEquals("zxxxxxxxxxxxxxxx", unpackagerString3);
    }

    @Test
    public void DigitsTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            RLE RLE = new RLE();
            RLE.packager("212");
        });

        assertEquals("Input string cannot contain digit sequences", exception.getMessage());
    }

    private boolean fileComparison (String exp, String act) throws IOException{
        File file_1 = new File(exp);
        File file_2 = new File(act);
        FileReader fr_1 = new FileReader(file_1);
        FileReader fr_2 = new FileReader(file_2);
        BufferedReader reader_1 = new BufferedReader(fr_1);
        BufferedReader reader_2 = new BufferedReader(fr_2);
        String line_1 = reader_1.readLine();
        String line_2 = reader_2.readLine();
        if (Objects.equals(line_1, line_2)){
            while (line_1 != null){
                line_1 = reader_1.readLine();
                line_2 =  reader_2.readLine();
                if (!Objects.equals(line_1, line_2)) return false;
            }
        }
        return true;
    }

    @Test
    public void packagerFileTest() throws IOException {
        String cmd = "-z -out src/test/resources/Tests/output_1.txt src/test/resources/Tests/input_1.txt";
        String exp = "src/test/resources/Tests/result_1.txt";
        String act = "src/test/resources/Tests/output_1.txt";

        RLEParser.main(cmd.split(" "));

        assertTrue(fileComparison(exp, act));
    }

    @Test
    public void unpackagerFileTest() throws IOException {
        String cmd = "-u -out src/test/resources/Tests/output_2.txt src/test/resources/Tests/input_2.txt";
        String exp = "src/test/resources/Tests/result_2.txt";
        String act = "src/test/resources/Tests/output_2.txt";

        RLEParser.main(cmd.split(" "));

        assertTrue(fileComparison(exp, act));
    }
}