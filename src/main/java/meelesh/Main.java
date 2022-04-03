package meelesh;

import meelesh.du.CmdLineParserCfg;
import org.kohsuke.args4j.CmdLineException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * пример: [-h] [-c] [--si] file1 file2 file3 …
 *  du - название программы
 *  -h - если флаг имеется, то вывести в читаемом виде(B, KB, MB, GB), иначе вывести в килобайтах без единицы измерения
 *  -c - вывести суммарный размер
 *  --si - взять основание 1000, а не 1024
 */

@ComponentScan
public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        CmdLineParserCfg parserCfg = context.getBean(CmdLineParserCfg.class);
        try {
            parserCfg.cmdLineParser().parseArgument(args);
            parserCfg.getDuParametersDto().run();
        } catch (CmdLineException  | IllegalArgumentException e) {
            System.err.println(e.getMessage());
            parserCfg.cmdLineParser().printUsage(System.err);
        }
    }
}
