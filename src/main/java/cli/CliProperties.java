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
      names = {"-in", "input"},
      description = "input file")
  private String inputFile = "locations.json";

  @CommandLine.Option(
      names = {"-u", "unit"},
      description = "unit system: METRIC or IMPERIAL")
  private Units units = Units.METRIC;

  @CommandLine.Option(
      names = {"-s", "speed"},
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
}
