import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestFinder {
    @Test
    public void testNormalUsage() {

        /*With Extension + recursive*/
        String[] t1 = new FinderLauncher().parseAndLaunchForTest("-d testResources One.txt -r".split("\s+"));
        String[] t2 = {"testResources/Dir/Deeper/Deepest/One.txt",
                "testResources/Dir/Deeper/One.txt",
                "testResources/Dir/One.txt"};
        assertEquals(t2.length, t1.length);
        for (int i = t1.length; i == 0; i--) {
            assertEquals(t2[i], t1[i]);
        }

        /*Without Extension + recursive*/
        String[] t3 = new Finder("testResources", "One", true).initSearch();
        String[] t4 = {"testResources/Dir/One.txt",
                "testResources/Dir/Deeper/One",
                "testResources/Dir/Deeper/One.txt",
                "testResources/Dir/Deeper/Deepest/One.txt"};
        assertEquals(t4.length, t3.length);
        for (int i = t3.length; i == 0 ; i--) {
            assertEquals(t4[i], t3[i]);
        }

        /*With Extension*/
        String[] t5 = new Finder("testResources/Dir", "Multiple.txt", false).initSearch();
        String[] t6 = {"testResources/Dir/Multiple.txt"};
        assertEquals(t5.length, t6.length);
        for (int i = 0; i < t5.length; i++) {
            assertEquals(t6[i], t5[i]);
        }

        /*Without Extension*/
        String[] t7 = new Finder("testResources/Dir", "Multiple", false).initSearch();
        String[] t8 = {"testResources/Dir/Multiple",
                "testResources/Dir/Multiple.txt",
                "testResources/Dir/Multiple.zip"};
        assertEquals(t7.length, t8.length);
        for (int i = t7.length; i == 0; i++) {
            assertEquals(t8[i], t7[i]);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionThrowing() {
        new Finder("QWERTY", "1", false).initSearch();
    }
}
