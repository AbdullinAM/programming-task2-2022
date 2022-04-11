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
        assertTrue(arraysEquals(t2, t1));


        /*Without Extension + recursive*/
        String[] t3 = new FinderLauncher().parseAndLaunchForTest("-d testResources One -r".split("\s+"));
        String[] t4 = {"testResources"+slash+"Dir"+slash+"Deeper"+slash+"Deepest"+slash+"One.txt",
                "testResources"+slash+"Dir"+slash+"Deeper"+slash+"One",
                "testResources"+slash+"Dir"+slash+"Deeper"+slash+"One.txt",
                "testResources"+slash+"Dir"+slash+"One.txt"};
        assertTrue(arraysEquals(t3, t4));

        /*With Extension*/
        String[] t5 = new FinderLauncher().parseAndLaunchForTest("-d testResources Multiple.txt".split("\s+"));
        String[] t6 = {"testResources"+slash+"Multiple.txt"};
        assertTrue(arraysEquals(t5, t6));

        /*Without Extension*/
        String[] t7 = new FinderLauncher().parseAndLaunchForTest("-d testResources Multiple".split("\s+"));
        String[] t8 = {"testResources"+slash+"Multiple",
                "testResources"+slash+"Multiple.txt",
                "testResources"+slash+"Multiple.zip"};
        assertTrue(arraysEquals(t7, t8));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionThrowing() {
        new Finder("QWERTY", "1", false).initSearch();
    }

    private boolean arraysEquals(String[] arr, String[] arr2){
        if (arr.length != arr2.length) {return false;}
        for (int i = 0; i < arr.length; i++) {
            boolean sameFound = false;
            for (int j = 0; j < arr.length; j++){
                if (arr[i].equals(arr[j])) {sameFound = true;}
            }
            if (sameFound == false) {return false;}
        }
        return true;
    }
}
