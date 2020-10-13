package cli;


import picocli.CommandLine;

public class CliProperties {
  @CommandLine.Parameters(index = "0", description = "input-file")
  int number;

  @CommandLine.Parameters(index = "1", description = "number-of-routes")
  int number2;

  @CommandLine.Parameters(index = "2", description = "max-route-length")
  int number3;

  @CommandLine.Option(names = "-unit", description = "unit system: km or mile")
  String units = "km";

  @CommandLine.Option(names = "-speed", description = "speed of a car")
  double speed = 90.0;

  @CommandLine.Option(names = "-itv", description = "interval rate")
  int interval = 180;
}
