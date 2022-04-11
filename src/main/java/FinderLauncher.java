import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        if (pathToDir.isBlank()) {
            pathToDir = Paths.get(".").toString();
        }

        File[] targetDir = new File(pathToDir).listFiles();
        if (targetDir == null) {
            throw new IllegalArgumentException("No such directory or directory is empty.");
        }
        Finder finder = new Finder(targetDir, fileName, recursive);
        String[] results = finder.initSearch();


        for (String str : results){
            System.out.println(str);
        }
    }

    /*"Public" instance of parseAndLaunch for testing*/
    public String[] parseAndLaunchForTest(String[] args) {
        CmdLineParser cmdParser = new CmdLineParser(this);

        try {
            cmdParser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            cmdParser.printUsage(System.err);
            return new String[]{};
        }

        if (pathToDir.isBlank()) {
            pathToDir = Paths.get(".").toString();
        }

        File[] targetDir = new File(pathToDir).listFiles();
        if (targetDir == null) {
            throw new IllegalArgumentException("No such directory or directory is empty.");
        }
        Finder finder = new Finder(targetDir, fileName, recursive);
        String[] results = finder.initSearch();

        return results;
    }
}
