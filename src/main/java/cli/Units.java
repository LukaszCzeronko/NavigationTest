package cli;

public enum Units {
  METRIC(1.621371192),
  IMPERIAL(1.00);
  private double unit;

  Units(double unit) {
    this.unit = unit;
  }

  public double getUnit() {

    return this.unit;
  }
}
