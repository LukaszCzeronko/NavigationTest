package cli;

public enum Units {
  METRIC("km"),
  IMPERIAL("mi");
  private String unit;

  Units(String unit) {
    this.unit = unit;
  }

  public String getUnit() {

    return this.unit;
  }
}
