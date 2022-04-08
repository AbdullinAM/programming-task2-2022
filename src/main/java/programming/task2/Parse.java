package programming.task2;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import java.io.*;
import java.util.List;

public class Parse {
    @Option(name = "-v", metaVar = "Find everything except word")
    private Boolean not = false;

    @Option(name = "-i", metaVar = "Ignore case of words")
    private Boolean ic = false;

    @Option(name = "-r", metaVar = "regexToFind", usage = "Input regex to find")
    private Boolean rg = false;

    @Argument( required = true, metaVar = "word", usage = "Input word to find")
    private String word;

    @Argument(required = true, index = 1, metaVar = "InputName", usage = "Input file name")
    private String inputName;

    public static void main(String[] args) {
        new Parse().launch(args);
    }
    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            try {
                Grep grep = new Grep();
                List<String> result;
                if (rg){
                    result = grep.GrepRg(not, ic, word, inputName);
                } else {
                    result = grep.Grep(not, ic, word, inputName);
                }
                for (String s: result) {
                    System.out.println(s);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println(" java -jar FindInText.jar  [-v] [-i] [-r] word InputName.txt ");
            parser.printUsage(System.err);
        }

    }
}
