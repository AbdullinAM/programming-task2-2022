package programming.task2;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;

public class TailLauncher {
    @Option(name = "-o", metaVar = "OFile", usage = "Output File")
    private String outputFile;

    @Option(name = "-c", metaVar = "CountSymbols", usage = "Count of symbols", forbids = {"-n"})
    private int countSymbols;

    @Option(name = "-n", metaVar = "CountStrings", usage = "Count of strings", forbids = {"-c"})
    private int countStrings;

    @Argument(metaVar = "InputNames", usage = "Input file's name")
    private String[] inputFileNames;

    public String readFile(InputStream in) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder text = new StringBuilder();
            String s = reader.readLine();
            text.append(s);
            while(s != null) {
                s = reader.readLine();
                if (s != null) {
                    text.append(System.getProperty("line.separator"));
                    text.append(s); }
            }
            return text.toString();
        }
        catch(IOException e){
            return null;
        }
    }

    public String readFile(String inputName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputName)) {
            return readFile(inputStream);

        }
    }

    public void writeFile(String text, String outputName) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(outputName)) {
            for (int i = 0; i < text.length(); i++){
                outputStream.write(text.charAt(i));
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) throws IOException {
        new TailLauncher().launch(args);
    }

    private void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }
        StringBuilder res = new StringBuilder();
        for (String inputFileName : inputFileNames) {
            Tail tail = new Tail();
            String text = readFile(inputFileName);
            if (inputFileNames.length > 1) {
                res.append(inputFileName);
                res.append(System.getProperty("line.separator")); }
            try {
                if (countSymbols > 0) {
                    res.append(tail.TailSymbols(text, countSymbols));
                }
                else {
                    if (countStrings > 0) {
                        res.append(tail.TailStrings(text, countStrings));
                    } else res.append(tail.TailStrings(text, 10));}
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
            res.append(System.getProperty("line.separator"));
        }
        writeFile(res.toString(), outputFile);
    }
}
