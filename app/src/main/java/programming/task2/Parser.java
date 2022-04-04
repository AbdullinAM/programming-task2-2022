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
    private boolean h;

    @Option(name = "-c", metaVar = "TotalSize")
    private boolean c;

    @Option(name = "--si", metaVar = "Base")
    private boolean si;

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
            du.fileEnumeration(inputNames, h, si, c);
        } catch (IllegalArgumentException e) {
            System.err.println("Файла нет");
            return 1;
        }
        return 0;
    }
}