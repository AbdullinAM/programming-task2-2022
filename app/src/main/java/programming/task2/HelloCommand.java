package programming.task2;

import picocli.CommandLine;
import java.io.File;
import static programming.task2.Base.calculateFileSize;
import static programming.task2.Base.calculateFullSize;


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

                if (h) System.out.println(calculateFileSize(files[0].length(), 1024));
                if (c) {
                    double sum = 0.0;
                    for (File file : files) {
                        sum += file.length();
                    }
                    System.out.println(calculateFullSize(files));
                    }
                if (si) System.out.println(calculateFileSize(files[0].length(), 1000));
                }
            }





