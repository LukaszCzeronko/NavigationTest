import cli.CliProperties;
import lombok.extern.slf4j.Slf4j;
import model.Location;
import picocli.CommandLine;
import summaryPackage.DefaultRequestCsv;
import summaryPackage.RouteCalculation;

import java.util.List;

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

        DefaultRequestCsv defaultRequestCsv = new DefaultRequestCsv();
        List<List<Location>> routePoints;
        routePoints = routeCalculation.calculatePoints(cliProperties);
        defaultRequestCsv.createDefaultCSV(routePoints);
      }
    } catch (CommandLine.ParameterException ex) {
      System.err.println(ex.getMessage());
      ex.getCommandLine().usage(System.err);
    }
  }
}
