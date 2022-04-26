import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RLETests {
    @Test
    public void packerStringTest() {
        RLE RLE = new RLE();
        String packString1 = RLE.packager("BBBBBBBBBBBBIIIIIIIIIIIGGGG DUBBBB MMMMMAAAAAAAAAAAATTTTTTEEEEEEEEE");
        assertEquals("12B11I4G 1D1U4B 5M12A6T9E", packString1);

        String packString2 = RLE.packager("TTTTTTTTTTTHHHHHIIIIIIIISSSS ISSSS AAA TTTTEEESSSSTTTT");
        assertEquals("11T5H8I4S 1I4S 3A 4T3E4S4T", packString2);
    }
}
