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
        List<String> results;
        if (new File(pathToDir).listFiles() == null) {
            throw new IllegalArgumentException("No such directory or directory is empty.");
        }

        File[] filesList = new File(pathToDir).listFiles();

        results = recursiveFind(filesList, pathToDir, fileName, ExtNeeded, recursive);

        return results.toArray(new String[0]);
    }
    private ArrayList<String> recursiveFind (File[] filesArray, String pathToDir, String fileName, Boolean extNeeded, Boolean recursive) {
        ArrayList<String> results = new ArrayList<>();
        for (File file : filesArray){
            if (file.isDirectory() && recursive){
                List<String> r = recursiveFind(file.listFiles(), pathToDir+"/"+file.getName(), fileName, extNeeded, recursive);
                for (String s : r) {
                    results.add(s);
                }
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
