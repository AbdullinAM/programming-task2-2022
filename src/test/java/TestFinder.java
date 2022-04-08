import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestFinder {
    @Test
    public void testNormalUsage() {

        String slash = "/";
        if (System.getProperty("os.name").contains("Windows")){slash = "\\";}

        /*With Extension + recursive*/
        String[] t1 = new FinderLauncher().parseAndLaunchForTest("-d testResources One.txt -r".split("\s+"));
        String[] t2 = {"testResources"+slash+"Dir"+slash+"Deeper"+slash+"Deepest"+slash+"One.txt",
                "testResources"+slash+"Dir"+slash+"Deeper"+slash+"One.txt",
                "testResources"+slash+"Dir"+slash+"One.txt"};
        assertEquals(t2.length, t1.length);
        for (int i = 0; i < t1.length; i++) {
            assertEquals(t2[i], t1[i]);
        }

        /*Without Extension + recursive*/
        String[] t3 = new Finder("testResources", "One", true).initSearch();
        String[] t4 = {"testResources"+slash+"Dir"+slash+"Deeper"+slash+"Deepest"+slash+"One.txt",
                "testResources"+slash+"Dir"+slash+"Deeper"+slash+"One",
                "testResources"+slash+"Dir"+slash+"Deeper"+slash+"One.txt",
                "testResources"+slash+"Dir"+slash+"One.txt"};
        assertEquals(t4.length, t3.length);
        for (int i = 0; i < t3.length ; i++) {
            assertEquals(t4[i], t3[i]);
        }

        /*With Extension*/
        String[] t5 = new Finder("testResources", "Multiple.txt", false).initSearch();
        String[] t6 = {"testResources"+slash+"Multiple.txt"};
        assertEquals(t5.length, t6.length);
        for (int i = 0; i < t5.length; i++) {
            assertEquals(t6[i], t5[i]);
        }

        /*Without Extension*/
        String[] t7 = new Finder("testResources", "Multiple", false).initSearch();
        String[] t8 = {"testResources"+slash+"Multiple",
                "testResources"+slash+"Multiple.txt",
                "testResources"+slash+"Multiple.zip"};
        assertEquals(t7.length, t8.length);
        for (int i = 0; i < t7.length; i++) {
            assertEquals(t8[i], t7[i]);
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionThrowing() {
        new Finder("QWERTY", "1", false).initSearch();
    }
}
