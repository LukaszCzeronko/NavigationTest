package cli;

public enum Units {
  METRIC(1.00),
  IMPERIAL(0.621371192);
  private double unit;

  Units(double unit) {
    this.unit = unit;
  }

  public double getUnit() {

    return this.unit;
  }
}
