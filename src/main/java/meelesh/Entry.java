package meelesh;

import java.util.HashMap;
import java.util.Map;

/**
 * пример: du [-h] [-c] [--si] file1 file2 file3 …
 *  du - название программы
 *  -h - если флаг имеется, то вывести в читаемом виде(B, KB, MB, GB), иначе вывести в килобайтах без единицы измерения
 *  -c - вывести суммарный размер
 *  --si - взять основание 1000, а не 1024
 */
public class Entry {

    public static void main(String[] args) {
        ParseArgs parseArgs = new ParseArgs(args);
        FileSizeReader fIleSizeReader = new FIleSizeReaderImpl();

        OutputGenerator outputGenerator = new OutputGenerator(parseArgs, fIleSizeReader);
        outputGenerator.print();

//        List<String> filePaths = parseArgs.parse();
//        List<Double> fileSizes = filePaths.stream()
//                .map(fIleSizeReader::getFileFromString)
//                .map(fIleSizeReader::getSizeBytes).toList();
//
//        for (int i = 0; i < filePaths.size(); i++) {
//            nameAndSize.put(filePaths.get(i), fileSizes.get(i));
//        }
//
//
    }
}
