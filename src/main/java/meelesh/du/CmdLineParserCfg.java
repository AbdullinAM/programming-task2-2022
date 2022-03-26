package meelesh.du;

import lombok.Data;
import org.kohsuke.args4j.CmdLineParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
public class CmdLineParserCfg {

    private DuParametersDto duParametersDto;

    @Autowired
    public CmdLineParserCfg(DuParametersDto duParametersDto) {
        this.duParametersDto = duParametersDto;
    }

    @Bean
    public CmdLineParser cmdLineParser() {
        return new CmdLineParser(duParametersDto);
    }
}
