package cli;

import calculation.CalculateCoordinatesInterpolationAzimuthLength;
import calculation.CalculateCoordinatesInterpolationHighAccuracy;
import calculation.CalculateCoordinatesInterpolationRoutePoints;
import lombok.AllArgsConstructor;
import model.Location;
import model.LocationPoint;

import java.util.List;

@AllArgsConstructor
public enum InterpolationType implements InterpolationInterface {
  HA {
    @Override
    public List<Location> getInterpolation(
        LocationPoint locationPoint, CliProperties cliProperties) {
      return CalculateCoordinatesInterpolationHighAccuracy.calculatePointsOnRoute(
          locationPoint, cliProperties.getUnits(), cliProperties.isDebug());
    }
  },
  AL {
    @Override
    public List<Location> getInterpolation(
        LocationPoint locationPoint, CliProperties cliProperties) {
      return CalculateCoordinatesInterpolationAzimuthLength.calculatePointsOnRoute(
          locationPoint, cliProperties.getUnits(), cliProperties.isDebug());
    }
  },
  RP {
    public List<Location> getInterpolation(
        LocationPoint locationPoint, CliProperties cliProperties) {
      return CalculateCoordinatesInterpolationRoutePoints.calculatePointsOnRoute(
          locationPoint, cliProperties.getUnits(), cliProperties.isDebug());
    }
  }
}
