package meelesh.du;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Math.pow;

@Data
@Component
public class OutputGenerator {

    private FileSizeReader fileSizeReader;

    private static final int defaultBase = 1024;
    private static final int siBase = 1000;

    private static final int GB = (int) pow(1024, 3);
    private static final int MB = (int) pow(1024, 2);
    private static final int KB = 1024;

    private List<String> files = new ArrayList<>();
    private final Map<String, Long> fileAndBytes = new HashMap<>();
    private Long totalSize = 0L;


    @Autowired
    public OutputGenerator(FileSizeReader fileSizeReader) {
        this.fileSizeReader = fileSizeReader;
    }

    public void exploreFiles() throws IllegalArgumentException {
        List<Long> fileSizes =
                files
                .stream()
                .map(fileSizeReader::getFileFromString)
                .map(fileSizeReader::getSizeBytes)
                .toList();

        for (int i = 0; i < files.size(); i++) {
            fileAndBytes.put(files.get(i), fileSizes.get(i));
            totalSize += fileSizes.get(i);
        }

        if (fileAndBytes.isEmpty()) {
            throw new IllegalArgumentException("file/files not found");
        }
    }

    public void print() throws IllegalArgumentException {
        exploreFiles();
        Function<Long, String> formatPrinter =
                DuParametersDto.h? this::convertToReadableFormat: this::convertToDefaultFormat;

        fileAndBytes
                .entrySet()
                .stream()
                .map(file -> {
                    System.out.print("file name: " + file.getKey() + ", size: ");
                    return file.getValue();
                })
                .map(formatPrinter)
                .forEach(System.out::println);

        if (DuParametersDto.c) System.out.println(calculateTotalSize(totalSize));
        System.exit(0);
    }

    public String calculateTotalSize(Long totalSizeBytes) {
        if (DuParametersDto.h)
            return "Total size: " + convertToReadableFormat(totalSizeBytes);
        return "Total size: " + new BigDecimal(convertToDefaultFormat(totalSizeBytes)).setScale(2, RoundingMode.HALF_UP);
    }

    public String convertToDefaultFormat(Long size) {
        var base = DuParametersDto.si? siBase: defaultBase;
        return String.valueOf(new BigDecimal((double) size / base).setScale(2, RoundingMode.HALF_UP));
    }

    public String convertToReadableFormat(Long size) {
        var base = DuParametersDto.si? siBase: defaultBase;

        if (size >= GB) return new BigDecimal(size / pow(base, 3)).setScale(2, RoundingMode.HALF_UP) + " GB";
        if (size >= MB) return new BigDecimal(size / pow(base, 2)).setScale(2, RoundingMode.HALF_UP)  + " MB";
        if (size >= KB) return new BigDecimal((double) size / base).setScale(2, RoundingMode.HALF_UP)  + " KB";
        return size + " B";
    }

}
