import cli.CliProperties;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import summaryPackage.RouteCalculation;

import java.util.concurrent.Callable;

@Slf4j
@CommandLine.Command(name = "Navigation", version = "Navigation 1.0", mixinStandardHelpOptions = true)
public class App implements Callable {

  public static void main(String[] args) {
    CliProperties cliProperties=new CliProperties();
     new CommandLine(cliProperties).parseArgs(args);

      //int exitCode = new CommandLine(new RouteCalculation()).execute(args);
      //System.exit(exitCode);

  }


    @Override
    public Object call() throws Exception {

        return null;
    }
}
