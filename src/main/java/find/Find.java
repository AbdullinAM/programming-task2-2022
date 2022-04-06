package find;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Find {
    @Option(name = "-d")
    private String directory;
    @Option(name = "-r")
    private boolean isAllPreDirection;
    @Argument()
    private String[] fileName;

    private ArrayList<File> fileList = new ArrayList<>();

    public ArrayList<File> DoMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Example: find [-r][-d directory] filename.txt");
        }

        find(new File(directory));

        for (int i = 0; i < fileList.size(); i++) {
            System.out.println(fileList.get(i));
        }

        if (fileList.isEmpty()) {
            System.out.println("file does not exist");
        }
        return fileList;//для тестов, массив с результатами
    }

    public void find(File rootFile) throws IOException {

        if (!rootFile.exists()) System.err.println("This directory does not exist");

        if (rootFile.isDirectory()) {
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (isAllPreDirection) {
                        if (file.isDirectory()) {
                            find(file);
                        } else {
                            someFunc(file);
                        }
                    } else {
                        someFunc(file);
                    }
                }
            } else System.out.println("This direction is empty");
        }
    }

    public void someFunc(File file) {
        for (int i = 0; i < fileName.length; i++) {
            if (file.getName().equals(fileName[i])) {
                fileList.add(file);
            }
        }
    }
}