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
        if (new File(pathToDir).listFiles() == null) {
            throw new IllegalArgumentException("No such directory or directory is empty.");
        }
        List<String> result;
        File[] filesList = new File(pathToDir).listFiles();
        if (fileName.contains(".")) {
            result = recursiveFind(filesList, pathToDir, fileName, true, recursive);
        } else {
            result = recursiveFind(filesList, pathToDir, fileName, false, recursive);}

        return result.toArray(new String[0]);
    }


    private ArrayList<String> recursiveFind (File[] filesArray, String pathToDir, String fileName, Boolean extNeeded, Boolean recursive) {
        ArrayList<String> results = new ArrayList<>();
        for (File file : filesArray){
            if (file.isDirectory() && recursive){
                results.addAll(recursiveFind(file.listFiles(), pathToDir+"/"+file.getName(), fileName, extNeeded, recursive));
            } else {
                String name = file.getName();
                if (!extNeeded) {
                    name = name.split("\\.")[0];
                }
                if (name.equals(fileName)) {
                    results.add(pathToDir+"/"+file.getName());
                }
            }
        }
        return results;
    }
}
