import cli.CliProperties;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import summaryPackage.RouteCalculation;

@Slf4j
@CommandLine.Command(
    name = "Navigation",
    version = "Navigation 1.0",
    mixinStandardHelpOptions = true)
public class App {

  public static void main(String[] args) {
    CliProperties cliProperties = new CliProperties();
    RouteCalculation routeCalculation = new RouteCalculation();
    new CommandLine(cliProperties).parseArgs(args);
    try {
      CommandLine.ParseResult parseResult = new CommandLine(cliProperties).parseArgs(args);
      if (!CommandLine.printHelpIfRequested(parseResult)) {
        routeCalculation.calculatePoints(cliProperties);
      }
    } catch (CommandLine.ParameterException ex) {
      System.err.println(ex.getMessage());
      ex.getCommandLine().usage(System.err);
    }
  }
}
