import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Finder {
    private File[] targetDir;

    private final String fileName;

    private final boolean recursive;


    public Finder(File[] targetDir, String fileName, boolean recursive) {
        this.targetDir = targetDir;
        this.fileName = fileName;
        this.recursive = recursive;
    }

    public List<String> initSearch(){
        List<String> result;
        result = recursiveFind(targetDir, fileName, recursive);
        return result;
    }


    private ArrayList<String> recursiveFind (File[] targetDir, String fileName, Boolean recursive) {
        ArrayList<String> results = new ArrayList<>();
        for (File file : targetDir){
            if (file.isDirectory() && recursive){
                results.addAll(recursiveFind(file.listFiles(), fileName, recursive));
            } else {
                String name = file.getName();
                if (name.equals(fileName) || name.split("\\.")[0].equals(fileName)) {
                    results.add(file.getPath());
                }
            }
        }
        return results;
    }
}
