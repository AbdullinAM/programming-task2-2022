package meelesh.du;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DuParametersDto {

    private final Starter starter;

    @Autowired
    public DuParametersDto(Starter starter) {
        this.starter = starter;
    }

    @Option(name="-h",usage="readable format")
    public static boolean h;

    @Option(name="-c",usage="print sum")
    public static boolean c;

    @Option(name="--si",usage="base 1000 instead of 1024")
    public static boolean si;

    @Argument(multiValued = true, usage="files or directories")
    public static List<String> paths = new ArrayList<>();

    public void run() {
        starter.start();
    }

}
