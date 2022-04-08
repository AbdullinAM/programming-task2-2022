package programming.task2;

import picocli.CommandLine;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("src//test//resources//text3.txt");
        new CommandLine(new HelloCommand()).execute("--si", file.toString());
    }
}
