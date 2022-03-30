package programming.task2;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    @Argument(required = true, metaVar = "InputName")
    private List<String> inputNames = new ArrayList<>();

    @Option(name = "-h", metaVar = "OutputFormat")
    private boolean outputFormat;

    @Option(name = "-c", metaVar = "TotalSize")
    private boolean totalSize;

    @Option(name = "--si", metaVar = "Base")
    private boolean base;

    public static void main(String[] args) {
        new Parser().launch(args);
    }
    private int launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar du.jar [-h] [-c] [--si] InputNames");
            parser.printUsage(System.err);
            return 1;
        }

        Du du = new Du();

        try {
            du.fileEnumeration(inputNames, outputFormat, base, totalSize);
        } catch (IllegalArgumentException e) {
            return 1;
        }
        return 0;
    }
}