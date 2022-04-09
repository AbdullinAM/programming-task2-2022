package programming.task2;

import picocli.CommandLine;


public class Main {
    public static void main(String[] args) {
        new CommandLine(new HelloCommand()).execute(args);
    }
}
