import java.util.List;

public class LocationPoint {
  private List<Double> pointLatitude;
  private List<Double> pointLongitude;
  private List<Double> pointElevation;
  private List<Double> countryDistance;
  private List<Double> pointDistance;
  private List<Double> pointAzimuth;
  private double step;

  public LocationPoint(
      List<Double> pointLatitude,
      List<Double> pointLongitude,
      List<Double> pointElevation,
      List<Double> countryDistance) {
    this.pointLatitude = pointLatitude;
    this.pointLongitude = pointLongitude;
    this.pointElevation = pointElevation;
    this.countryDistance = countryDistance;
  }

  public LocationPoint(
      List<Double> pointLatitude,
      List<Double> pointLongitude,
      List<Double> pointDistance,
      List<Double> pointAzimuth,
      double step) {
    this.pointLatitude = pointLatitude;
    this.pointLongitude = pointLongitude;
    this.pointDistance = pointDistance;
    this.pointAzimuth = pointAzimuth;
    this.step = step;
  }

  public List<Double> getPointLatitude() {
    return pointLatitude;
  }

  public void setPointLatitude(List<Double> pointLatitude) {
    this.pointLatitude = pointLatitude;
  }

  public List<Double> getPointLongitude() {
    return pointLongitude;
  }

  public void setPointLongitude(List<Double> pointLongitude) {
    this.pointLongitude = pointLongitude;
  }

  public List<Double> getPointElevation() {
    return pointElevation;
  }

  public void setPointElevation(List<Double> pointElevation) {
    this.pointElevation = pointElevation;
  }

  public List<Double> getCountryDistance() {
    return countryDistance;
  }

  public void setCountryDistance(List<Double> countryDistance) {
    this.countryDistance = countryDistance;
  }

  public List<Double> getPointDistance() {
    return pointDistance;
  }

  public void setPointDistance(List<Double> pointDistance) {
    this.pointDistance = pointDistance;
  }

  public List<Double> getPointAzimuth() {
    return pointAzimuth;
  }

  public void setPointAzimuth(List<Double> pointAzimuth) {
    this.pointAzimuth = pointAzimuth;
  }

  public double getStep() {
    return step;
  }

  public void setStep(double step) {
    this.step = step;
  }
}
