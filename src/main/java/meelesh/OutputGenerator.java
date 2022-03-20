package meelesh;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.pow;

@NoArgsConstructor
@Data
public class OutputGenerator {

    private ParseArgs parseArgs;
    private FileSizeReader fileSizeReader;
    private final int defaultBase = 1024;
    private final int siBase = 1000;
    private final Map<String, Double> fileAndBytes = new HashMap<>();
    private Double totalSize = 0.0;
    
    public OutputGenerator(ParseArgs parseArgs, FileSizeReader fileSizeReader) {
        this.parseArgs = parseArgs;
        this.fileSizeReader = fileSizeReader;
        exploreFiles();
    }

    private void exploreFiles() {
        List<String> filePaths = parseArgs.parse();
        List<Double> fileSizes =
                filePaths
                .stream()
                .map(fileSizeReader::getFileFromString)
                .map(fileSizeReader::getSizeBytes)
                .toList();

        for (int i = 0; i < filePaths.size(); i++) {
            fileAndBytes.put(filePaths.get(i), fileSizes.get(i));
            totalSize += fileSizes.get(i);
        }

        if (fileAndBytes.isEmpty()) {
            System.out.println("file/files not found");
            System.exit(1);
        }
    }

    public void print() {

        if (parseArgs.isH()) {
            if (parseArgs.isSi()) {
                fileAndBytes
                        .entrySet()
                        .stream()
                        .map(file -> "file name: " + file.getKey() + ", size: " + convertToReadableFormat(file.getValue(), siBase))
                        .forEach(System.out::println);
            } else {
                fileAndBytes
                        .entrySet()
                        .stream()
                        .map(file -> "file name: " + file.getKey() + ", size: " + convertToReadableFormat(file.getValue(), defaultBase))
                        .forEach(System.out::println);
            }
            if (parseArgs.isC()) System.out.println(calculateTotalSize(totalSize));
            System.exit(0);
        }

        if (parseArgs.isSi()) {
            fileAndBytes
                    .entrySet()
                    .stream()
                    .map(file -> "file name: " + file.getKey() + ", size: " + file.getValue() / siBase)
                    .forEach(System.out::println);
            if (parseArgs.isC()) System.out.println(calculateTotalSize(totalSize));
            System.exit(0);
        }

        fileAndBytes
                .entrySet()
                .stream()
                .map(file -> "file name: " + file.getKey() + ", size: " + String.format("%.2f", file.getValue() / defaultBase))
                .forEach(System.out::println);
        if (parseArgs.isC()) System.out.println(calculateTotalSize(totalSize));
        System.exit(0);
    }

    public String calculateTotalSize(double totalSizeBytes) {
        if (parseArgs.isH() && parseArgs.isSi()) return "Total size: " + convertToReadableFormat(totalSizeBytes, siBase);
        if (parseArgs.isH() && !parseArgs.isSi()) return "Total size: " + convertToReadableFormat(totalSizeBytes, defaultBase);
        if (parseArgs.isSi()) return "Total size: " + String.format("%.2f", totalSizeBytes / siBase);
        return "Total size: " +  String.format("%.2f", (totalSizeBytes / defaultBase));
    }

    public String convertToReadableFormat(Double size, int base) {
        if (size >= 1073741824) return String.format("%.2f", (size / pow(base, 3))) + " GB";
        if (size >= 1048576) return String.format("%.2f", (size / pow(base, 2))) + " MB";
        if (size >= 1024) return String.format("%.2f", (size / base)) + " KB";
        return size + " B";
    }

}
