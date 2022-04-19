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
        List<String> results = new ArrayList<>();
        for (File file : targetDir){
            if (file.isDirectory() && recursive) {
                results.addAll(recursiveFind(targetDir, fileName));
            } else {
                String name = file.getName();
                if (name.equals(fileName) || name.split("\\.")[0].equals(fileName)) {
                    results.add(file.getName()); //По тз выводим только имя если файл в указанной папке
                }
            }
        }
        return results;
    }


    private ArrayList<String> recursiveFind (File[] targetDir, String fileName) {
        ArrayList<String> results = new ArrayList<>();
        for (File file : targetDir){
            if (file.isDirectory()){
                results.addAll(recursiveFind(file.listFiles(), fileName));
            } else {
                String name = file.getName();
                if (name.equals(fileName) || name.split("\\.")[0].equals(fileName)) {
                    results.add(file.getPath()); //Выводим путь так как файл найден в подпапке
                }
            }
        }
        return results;
    }
}
