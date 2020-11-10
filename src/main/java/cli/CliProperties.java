package cli;

import lombok.Getter;
import picocli.CommandLine;

@Getter
public class CliProperties {

  @CommandLine.Parameters(index = "0", description = "number-of-routes")
  private int numberOfRoutes;

  @CommandLine.Parameters(index = "1", description = "max-route-length")
  private double maxRouteLength;
  // input file must be in resource folder. If there is no option read from
  // src\main\resources\locations.json
  @CommandLine.Option(
      names = {"-in", "-input"},
      description = "input file")
  private final String inputFile = "locations.json";

  @CommandLine.Option(
      names = {"-u", "-unit"},
      description = "unit system: METRIC or IMPERIAL")
  private final Units units = Units.METRIC;

  @CommandLine.Option(
      names = {"-s", "-speed"},
      description = "speed of a car")
  private final double speed = 90.0;

  @CommandLine.Option(
      names = {"-i", "-interval"},
      description = "interval rate")
  private final int interval = 180;
  // If path is given save file path localisation, in other case write file in main app folder
  @CommandLine.Option(
      names = {"-o", "-output"},
      description = "output file")
  private final String outputFile = "route.json";

  @CommandLine.Option(
      names = {"-c", "-config"},
      description = "output csv configuration path")
  private final String configPath = "configuration.json";

  @CommandLine.Option(
      names = {"-r", "results"},
      description = "csv result file")
  private final String csvPath = "csvRouteRequest.csv";

  @CommandLine.Option(
      names = {"-d", "-debug"},
      description = "turn on debug mode")
  private final boolean debug = false;
}
