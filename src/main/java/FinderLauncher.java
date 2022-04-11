import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FinderLauncher {
    @Option(name="-r",usage="Also search in subdirectories")
    private boolean recursive;

    @Option(name="-d",usage="Directory to search in",metaVar="Directory")
    private String pathToDir = ".";

    @Argument(required = true, usage = "Name of file", metaVar = "fileName")
    private String fileName;

    public boolean isRecursive() {
        return recursive;
    }

    public String getPathToDir() {
        return pathToDir;
    }

    public String getFileName() {
        return fileName;
    }

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


        File[] targetDir = new File(pathToDir).listFiles();
        if (targetDir == null) {
            throw new IllegalArgumentException("No such directory or directory is empty.");
        }
        Finder finder = new Finder(targetDir, fileName, recursive);
        List<String> results = finder.initSearch();


        for (String str : results){
            System.out.println(str);
        }
    }

    /*"Public" instance of parseAndLaunch for testing*/
    public List<String> parseAndLaunchForTest(String[] args) {
        CmdLineParser cmdParser = new CmdLineParser(this);

        try {
            cmdParser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println(e.getMessage());
            cmdParser.printUsage(System.err);
            return new ArrayList<>();
        }


        File[] targetDir = new File(pathToDir).listFiles();
        if (targetDir == null) {
            throw new IllegalArgumentException("No such directory or directory is empty.");
        }
        Finder finder = new Finder(targetDir, fileName, recursive);

        return finder.initSearch();
    }
}
