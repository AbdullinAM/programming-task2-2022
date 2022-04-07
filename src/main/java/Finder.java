import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Finder {
    private String pathToDir;

    private final String fileName;

    private final boolean recursive;

    public Finder(String pathToDir, String fileName, boolean recursive) {
        this.pathToDir = pathToDir;
        this.fileName = fileName;
        this.recursive = recursive;
    }

    public String[] initSearch(){
        if (pathToDir.equals("")){
            pathToDir = System.getProperty("user.dir");
        }
        String[] result = null;
        if (fileName.contains(".")) {result = find(true);} else {result = find(false);}
        return result;
    }

    private String[] find( boolean ExtNeeded ) {
        /* Sorting files and directories */
        ArrayList<String> results = new ArrayList<>();
        ArrayList<String> namesOfFiles = new ArrayList<>();
        ArrayList<String> namesOfDirs = new ArrayList<>();
        if (new File(pathToDir).listFiles() == null) {
            throw new IllegalArgumentException("No such directory or directory is empty.");
        }
        for (File file : new File(pathToDir).listFiles()) {
            if (file.isFile()) { namesOfFiles.add(file.getName());}
            else { namesOfDirs.add(file.getName());}
        }
        /* Finding essential files */
        String name = null;
        for (String nameOfFile : namesOfFiles) {
            if (ExtNeeded) {
                name = nameOfFile;
            } else {
                name = nameOfFile.split("\\.")[0];
            }
            if (name.equals(fileName)) {
                results.add(pathToDir + "\\" + nameOfFile);
            }
        }
        /* Initializing recursive search */
        if (!namesOfDirs.isEmpty() && recursive) {
            for (String dir : namesOfDirs){
                Finder f = new Finder(pathToDir+"\\"+dir, fileName, recursive);
                results.addAll(List.of(f.find(ExtNeeded)));
            }
        }
        return results.toArray(new String[0]);
    }
}
