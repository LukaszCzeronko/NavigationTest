package cli;

import model.Location;
import model.LocationPoint;

import java.util.List;

public interface InterpolationInterface {
  public List<Location> getInterpolation(LocationPoint locationPoint, CliProperties cliProperties);
}
