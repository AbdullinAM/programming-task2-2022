package programming.task2;

import picocli.CommandLine;


public class Main {
    public static void main(String[] args) {
//        String result = "";
//        for (String arg : args) {
//            result += arg;
//        }
//        String lastWord = result.substring(result.lastIndexOf(" ")+1);
        new CommandLine(new HelloCommand()).execute(args);
    }
}
