package cli;

import lombok.Getter;
import picocli.CommandLine;

@Getter
public class CliProperties {

  @CommandLine.Option(
      names = {"-rn"},
      required = true,
      description = "number-of-routes")
  private int numberOfRoutes;

  @CommandLine.Option(
      names = {"-ml"},
      required = true,
      description = "max-route-length")
  private double maxRouteLength;
  // input file must be in resource folder. If there is no option read from
  // src\main\resources\locations.json
  @CommandLine.Option(
      names = {"-in", "-input"},
      description = "input file")
  private String inputFile = "locations.json";

  @CommandLine.Option(
      names = {"-u", "-unit"},
      description = "unit system: METRIC or IMPERIAL")
  private Units units = Units.METRIC;

  @CommandLine.Option(
      names = {"-s", "-speed"},
      description = "speed of a car")
  private double speed = 90.0;

  @CommandLine.Option(
      names = {"-i", "-interval"},
      description = "interval rate")
  private int interval = 180;
  // If path is given save file path localisation, in other case write file in main app folder
  @CommandLine.Option(
      names = {"-o", "-output"},
      description = "output file")
  private String outputFile = "route.json";

  @CommandLine.Option(
      names = {"-c", "-config"},
      description = "input csv configuration path")
  private String configPath = "configuration.json";

  @CommandLine.Option(
      names = {"-r", "results"},
      description = "csv result file")
  private String csvPath = "csvRouteRequest.csv";

  @CommandLine.Option(
      names = {"-d", "-debug"},
      description = "turn on debug mode")
  private boolean debug = false;

  @CommandLine.Option(
      names = {"-a", "-algorithm"},
      description = "chose type of algorithm to Interpolate route")
  private InterpolationType interpolationType = InterpolationType.HA;
}
