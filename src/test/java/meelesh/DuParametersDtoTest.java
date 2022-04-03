package meelesh;

import meelesh.du.CmdLineParserCfg;
import meelesh.du.DuParametersDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kohsuke.args4j.CmdLineException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Main.class})
public class DuParametersDtoTest {

    private final CmdLineParserCfg cmdLineParserCfg;

    @Autowired
    public DuParametersDtoTest(CmdLineParserCfg cmdLineParserCfg) {
        this.cmdLineParserCfg = cmdLineParserCfg;
    }

    @Test
    public void parametersTest() throws CmdLineException {
        String[] args1 = new String[] { "--si", "-h", "-c" };

        cmdLineParserCfg.cmdLineParser().parseArgument(args1);

        assertTrue(DuParametersDto.si && DuParametersDto.h && DuParametersDto.c);

        DuParametersDto.c = false;
        DuParametersDto.si = false;
        DuParametersDto.h = false;

        String[] args2 = new String[] { "sadasdasdasd", "-h", "-c" };

        cmdLineParserCfg.cmdLineParser().parseArgument(args2);

        assertTrue(DuParametersDto.h);
        assertFalse(DuParametersDto.si);
        assertTrue(DuParametersDto.c);

    }

}
