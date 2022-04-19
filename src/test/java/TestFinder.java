import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;


public class TestFinder {
    @Test
    public void testNormalUsage() {

        String slash = "/";
        if (System.getProperty("os.name").contains("Windows")){slash = "\\";}

        /*With Extension + recursive*/
        List<String> t1 = new FinderLauncher().parseAndLaunchForTest("-d testResources One.txt -r".split("\s+"));
        List<String> t2 = new ArrayList<>(List.of(new String[]
                {"testResources" + slash + "Dir" + slash + "Deeper" + slash + "Deepest" + slash + "One.txt",
                "testResources" + slash + "Dir" + slash + "Deeper" + slash + "One.txt",
                "testResources" + slash + "Dir" + slash + "One.txt"}));
        assertTrue(arraysEquals(t2, t1));


        /*Without Extension + recursive*/
        List<String> t3 = new FinderLauncher().parseAndLaunchForTest("-d testResources One -r".split("\s+"));
        List<String> t4 = new ArrayList<>(List.of(new String[]
                {"testResources" + slash + "Dir" + slash + "Deeper" + slash + "Deepest" + slash + "One.txt",
                "testResources" + slash + "Dir" + slash + "Deeper" + slash + "One",
                "testResources" + slash + "Dir" + slash + "Deeper" + slash + "One.txt",
                "testResources" + slash + "Dir" + slash + "One.txt"}));
        assertTrue(arraysEquals(t4, t3));

        /*With Extension*/
        List<String> t5 = new FinderLauncher().parseAndLaunchForTest("-d testResources Multiple.txt".split("\s+"));
        List<String> t6 = new ArrayList<>();
        t6.add("Multiple.txt");
        assertTrue(arraysEquals(t6, t5));

        /*Without Extension*/
        List<String> t7 = new FinderLauncher().parseAndLaunchForTest("-d testResources Multiple".split("\s+"));
        List<String> t8 = new ArrayList<>(List.of(new String[]
                {"Multiple",
                "Multiple.txt",
                "Multiple.zip"}));
        assertTrue(arraysEquals(t8, t7));

    }

    @Test
    public void testArgumentParsing() throws CmdLineException {
        /*Specified dir + recursive*/
        FinderLauncher classUnderTest = new FinderLauncher();
        CmdLineParser cmdLineParser = new CmdLineParser(classUnderTest);
        cmdLineParser.parseArgument("-d testResources/Dir/Deeper Multiple -r".split("\s+"));
        assertEquals("testResources/Dir/Deeper", classUnderTest.getPathToDir());
        assertEquals("Multiple", classUnderTest.getFileName());
        assertTrue(classUnderTest.isRecursive());

        /*Non-Specified dir + non-recursive*/
        classUnderTest = new FinderLauncher();
        cmdLineParser = new CmdLineParser(classUnderTest);
        cmdLineParser.parseArgument("Multiple -r ".split("\s+"));
        assertEquals(".", classUnderTest.getPathToDir());
        assertEquals("Multiple", classUnderTest.getFileName());
        assertTrue(classUnderTest.isRecursive());
    }

    @Test
    public void testTooManyArguments() {
        CmdLineException exception = assertThrows(CmdLineException.class, () -> {
            FinderLauncher classUnderTest = new FinderLauncher();
            CmdLineParser cmdLineParser = new CmdLineParser(classUnderTest);
            cmdLineParser.parseArgument("-d D File -r zxc".split("\s+"));
        });
        assertTrue(exception.getMessage().contains("Too many arguments: zxc"));
    }

    @Test
    public void testNotExistingDir() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            new FinderLauncher().parseAndLaunchForTest("-d DDD Multiple".split("\s+"))
        );
        assertTrue(exception.getMessage().contains("Provided directory doesn't exist.\nYou are trying to reach : " +
                System.getProperty("user.dir") + "/DDD"));
    }

    @Test
    public void testProvidedDirIsFile() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new FinderLauncher().parseAndLaunchForTest("-d testResources/Multiple fileName".split("\s+"))
        );
        assertTrue(exception.getMessage().contains("Provided directory is a file"));
    }

    private boolean arraysEquals(List<String> arr, List<String> arr2){
        if (arr.size() != arr2.size()) {return false;}
        for (int i = 0; i < arr.size(); i++) {
            boolean sameFound = false;
            for (String s : arr) {
                if (arr2.get(i).equals(s)) {
                    sameFound = true;
                    break;
                }
            }
            if (!sameFound) {return false;}
        }
        return true;
    }
}
