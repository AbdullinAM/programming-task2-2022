package meelesh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputGenerator {

    private ParseArgs parseArgs;
    private FileSizeReader fileSizeReader;
    private Double totalSize = 0.0;

    Map<String, Double> fileAndBytes = new HashMap<>();
    Map<String, String> result = new HashMap<>();

    public OutputGenerator(ParseArgs parseArgs, FileSizeReader fileSizeReader) {
        this.parseArgs = parseArgs;
        this.fileSizeReader = fileSizeReader;
        exploreFiles();
    }


    private void exploreFiles() {
        parseArgs.parse();
        List<String> filePaths = parseArgs.parse();
        List<Double> fileSizes = filePaths.stream()
                .map(fileSizeReader::getFileFromString)
                .map(fileSizeReader::getSizeBytes).toList();

        for (int i = 0; i < filePaths.size(); i++) {
            fileAndBytes.put(filePaths.get(i), fileSizes.get(i));
            totalSize += fileSizes.get(i);
        }
    }

    public void print() {

        String totalSizeString = "";

        if (parseArgs.isExistSi() && parseArgs.isExistH()) {
            fileAndBytes.entrySet().forEach(file -> file.getValue());
        }

        if (parseArgs.isExistC()) {
            System.out.println("");
        }

        if (parseArgs.isExistH()) {

        }


        System.out.println("Total size: " + totalSizeString);
        result.entrySet().forEach(file -> System.out.println("File name: " + file.getKey() + " size: " + file.getValue()));
    }

    private String convertToReadableFormat(Double size) {

    }

}
