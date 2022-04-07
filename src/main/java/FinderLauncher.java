import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;

public class FinderLauncher {
    @Option(name="-r",usage="Also search in subdirectories")
    private boolean recursive;

    @Option(name="-d",usage="Directory to search in",metaVar="Directory")
    private String pathToDir = "";

    @Argument(required = true, usage = "Name of file", metaVar = "fileName")
    private String fileName;

    public static void main(String[] args) {
        try {
            new FinderLauncher().parseAndLaunch(args);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void parseAndLaunch(String[] args) {
        CmdLineParser cmdParser = new CmdLineParser(this);

        try {
            cmdParser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            cmdParser.printUsage(System.err);
            return;
        }

        Finder finder = new Finder(pathToDir, fileName, recursive);
        String[] results = finder.initSearch();

        for (String str : results){
            System.out.println(str);
        }
    }

    public String[] parseAndLaunchForTest(String[] args) {
        CmdLineParser cmdParser = new CmdLineParser(this);

        try {
            cmdParser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            cmdParser.printUsage(System.err);
            return new String[]{};
        }

        Finder finder = new Finder(pathToDir, fileName, recursive);
        String[] results = finder.initSearch();

        return results;
    }
}
