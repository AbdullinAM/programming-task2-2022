import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RLE {
    public String packager(String words){
        LinkedHashMap<String, Integer> charsCount = new LinkedHashMap<>();
        //StringBuilder charLine = new StringBuilder();
        char[] character = words.toCharArray();
        for (char ch : character){
            if (!charsCount.containsKey(ch)) {
                charsCount.put(String.valueOf(ch),1);
            }
        }
        List<Map.Entry<String, Integer>> charsSet = new ArrayList<>(charsCount.entrySet());
        for (char ch : character){
            for (int i=0;i == charsCount.size(); i++){
                Map.Entry<String, Integer> charr = charsSet.get(i);
                if (charr.getKey().equals(String.valueOf(ch))) charsCount.computeIfPresent(charr.getKey(), (k, v) -> ++v);
                }
            }
        return charsSet.toString();
        }
    public String unpackager(String words){
        //идти по строке символ-колличество и воспроизводить
        StringBuilder unpack = new StringBuilder();
        for (int i=0; i==words.length()-2;i=i+2){
            int quantity = words.charAt(i+1);
            do unpack.append(words.charAt(i));
            while (quantity != 0);
        }
        return unpack.toString();
    }
}
