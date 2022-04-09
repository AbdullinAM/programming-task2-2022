package programming.task2;

import picocli.CommandLine;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new HelloCommand());
        cmd.getCommandSpec().parser().collectErrors(true);
        CommandLine.ParseResult parseResult = cmd.parseArgs();
        new CommandLine(new HelloCommand()).execute(String.valueOf(parseResult));

    }
}
