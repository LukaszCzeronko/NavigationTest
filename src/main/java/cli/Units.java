package cli;

public enum Units {
  METRIC(1.00),
  IMPERIAL(1.621371192);
  private final double unit;

  Units(double unit) {
    this.unit = unit;
  }

  public double getUnit() {

    return this.unit;
  }
}
