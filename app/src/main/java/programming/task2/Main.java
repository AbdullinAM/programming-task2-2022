package programming.task2;

import picocli.CommandLine;


public class Main {
    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new HelloCommand());
        cmd.getCommandSpec().parser().collectErrors(true);
        CommandLine.ParseResult parseResult = cmd.parseArgs();
        new CommandLine(new HelloCommand()).execute(String.valueOf(parseResult));

    }
}
