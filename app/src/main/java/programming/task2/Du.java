package programming.task2;

import picocli.CommandLine;
import java.io.File;

import static programming.task2.Base.calculateFileSize;
import static programming.task2.Base.calculateFullSize;


public class Du {
    public static void main(String[] args) {
        new CommandLine(new HelloCommand()).execute( "--si", "-h", "C://nastya//test//text2.txt");
    }
}

    @CommandLine.Command(name = "du")
    class HelloCommand implements Runnable {

        @CommandLine.Option(names = "-h", description = "The size will be in human-readable format.")
        private static boolean h;

        @CommandLine.Option(names = "-c", description = "Put at least 2 files to see total" +
                " size for all files passed.")
        private static boolean c;

        @CommandLine.Option(names = "--si", description = "New base is 1000 now.")
        private static boolean si;

        @CommandLine.Parameters(arity = "1..*", description = "Put at least 1 file.")
        private static File[] files;

        @Override
        public void run() {
            for (File file : files) {
                if (!file.exists()) System.exit(1);
                if (h) System.out.println(calculateFileSize(files.length, 1024));
                if (c) System.out.println(calculateFullSize(files));
                if (si) System.out.println(calculateFileSize(files.length, 1000));

            }
        }
    }
