package programming.task2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;


public class Grep {

    public List<String> Grep(Boolean not, Boolean ic, String word, String  inputName) throws IOException {
        File file = new File(inputName);
        List<String> filtered = new ArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (ic) {
                    String lineIc = line.toLowerCase();
                    word = word.toLowerCase();
                    if (!not && lineIc.contains(word)) {
                        filtered.add(line);
                    } else if (not && !lineIc.contains(word)) {
                        filtered.add(line);
                    }
                } else if (!not && line.contains(word)) {
                    filtered.add(line);
                } else if (not && !line.contains(word)) {
                    filtered.add(line);
                }
            }
        }
        return filtered;
    }


    public List<String> GrepRg(Boolean not, Boolean ic, String word, String  inputName) throws IOException {
        File file = new File(inputName);
        List<String> filtered = new ArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null){
                if (ic){
                    Pattern pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
                    Matcher matcher = pattern.matcher(line);
                    if (!not && matcher.find()){
                        filtered.add(line);
                    } else if (not && !matcher.find()){
                        filtered.add(line);
                    }
                }else {
                    Pattern pattern = Pattern.compile(word);
                    Matcher matcher = pattern.matcher(line);
                    if (!not && matcher.find()) {
                        filtered.add(line);
                    } else if (not && !matcher.find()) {
                        filtered.add(line);
                    }
                }
            }
        }
        return filtered;
    }
}
