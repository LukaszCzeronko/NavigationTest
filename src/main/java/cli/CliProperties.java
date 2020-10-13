package cli;

import lombok.Getter;
import picocli.CommandLine;

@Getter
public class CliProperties {
  @CommandLine.Parameters(index = "0", description = "input-file")
  private String inputFile;

  @CommandLine.Parameters(index = "1", description = "number-of-routes")
  private int numberOfRoutes;

  @CommandLine.Parameters(index = "2", description = "max-route-length")
  private double maxRouteLength;

  @CommandLine.Option(
      names = {"-u", "unit"},
      description = "unit system: km or mile")
  private Units units = Units.METRIC;

  @CommandLine.Option(
      names = {"-s", "speed"},
      description = "speed of a car")
  double speed = 90.0;

  @CommandLine.Option(
      names = {"-i", "-interval"},
      description = "interval rate")
  private int interval = 180;

  @CommandLine.Option(
      names = {"o", "output"},
      description = "output-file")
  private String outputFile;
}
