package meelesh.du;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Component
public class Starter {

    private List<String> paths;
    private Set<String> allPaths = new HashSet<>();

    private FileSizeReader fileSizeReader;
    private OutputGenerator outputGenerator;

    @Autowired
    private Starter(FileSizeReader fileSizeReader, OutputGenerator outputGenerator) {
        this.fileSizeReader = fileSizeReader;
        this.outputGenerator = outputGenerator;
    }

    public void start() throws IllegalArgumentException {
        paths = DuParametersDto.paths;

        parsePaths(paths);
        outputGenerator.print();

    }

    public void parsePaths(List<String> paths) {
        paths.forEach(this::sortFilesAndCatalogs);
        findAllFiles(allPaths);
    }

    private void sortFilesAndCatalogs(String arg) {
        if (isFile(arg)) allPaths.add(arg);
        else {
            List<String> insideCatalog = searchInside(arg);
            allPaths.addAll(insideCatalog);
            insideCatalog.forEach(this::sortFilesAndCatalogs);
        }
    }

    private List<String> searchInside(String catalog) {
        List<String> paths = new ArrayList<>();
        File[] fileList = getFileList(catalog);
        for(File file : fileList) {
            paths.add(Paths.get(file.getAbsolutePath()).toString());
        }
        return paths;
    }

    private File[] getFileList(String dirPath) {
        File dir = new File(dirPath);
        File[] fileList = dir.listFiles();
        return fileList == null ? new File[] {} : fileList;
    }

    public static boolean isFile(String fileString) {
        return new File(fileString).isFile();
    }


    private void findAllFiles(Set<String> paths) {
        paths.stream().filter(Starter::isFile).forEach(filePath -> outputGenerator.getFiles().add(filePath));
    }
}