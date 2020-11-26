package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Setter
@Getter
public class RequestConfig {
  public static final String parameter = "/message?";

  private int ratio = 0;
  private String tp = "ET";
  private String id;
  private int df = 3;
  private String ver = "FFFF";
  private long lat;
  private long lon;
  private String app = "TT";
  private String gd = "N";

  public RequestConfig(
      int ratio,
      String tp,
      String id,
      int df,
      String ver,
      long lat,
      long lon,
      String app,
      String gd) {
    this.ratio = ratio;
    this.tp = tp;
    this.id = id;
    this.df = df;
    this.ver = ver;
    this.lat = lat;
    this.lon = lon;
    this.app = app;
    this.gd = gd;
  }

  public RequestConfig() {}

  public String toString() {
    return parameter
        + "tp="
        + this.getTp()
        + "&id="
        + this.getId()
        + "&df="
        + this.getDf()
        + "&ver="
        + this.getVer()
        + "&lat="
        + this.getLat()
        + "&lon="
        + this.getLon()
        + "&app="
        + this.getApp()
        + "&gd="
        + this.getGd();
  }
}
