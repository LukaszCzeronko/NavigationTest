package model;

public class PostRequest {
  public static final String parameter = "/message?";

  private String tp = "ET";
  private String id;
  private int df = 3;
  private String ver = "FFFF";
  private long lat;
  private long lon;
  private String app = "TT";
  private String gd = "N";

  public PostRequest(
      String tp, String id, int df, String ver, long lat, long lon, String app, String gd) {
    this.tp = tp;
    this.id = id;
    this.df = df;
    this.ver = ver;
    this.lat = lat;
    this.lon = lon;
    this.app = app;
    this.gd = gd;
  }

  public PostRequest() {}

  public String getTp() {
    return this.tp;
  }

  public String getId() {
    return this.id;
  }

  public int getDf() {
    return this.df;
  }

  public String getVer() {
    return this.ver;
  }

  public long getLat() {
    return this.lat;
  }

  public long getLon() {
    return this.lon;
  }

  public String getApp() {
    return this.app;
  }

  public String getGd() {
    return this.gd;
  }

  public void setTp(String tp) {
    this.tp = tp;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setDf(int df) {
    this.df = df;
  }

  public void setVer(String ver) {
    this.ver = ver;
  }

  public void setLat(long lat) {
    this.lat = lat;
  }

  public void setLon(long lon) {
    this.lon = lon;
  }

  public void setApp(String app) {
    this.app = app;
  }

  public void setGd(String gd) {
    this.gd = gd;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof PostRequest)) return false;
    final PostRequest other = (PostRequest) o;
    if (!other.canEqual((Object) this)) return false;
    final Object this$TP = this.getTp();
    final Object other$TP = other.getTp();
    if (this$TP == null ? other$TP != null : !this$TP.equals(other$TP)) return false;
    final Object this$ID = this.getId();
    final Object other$ID = other.getId();
    if (this$ID == null ? other$ID != null : !this$ID.equals(other$ID)) return false;
    if (this.getDf() != other.getDf()) return false;
    final Object this$VER = this.getVer();
    final Object other$VER = other.getVer();
    if (this$VER == null ? other$VER != null : !this$VER.equals(other$VER)) return false;
    if (this.getLat() != other.getLat()) return false;
    if (this.getLon() != other.getLon()) return false;
    final Object this$APP = this.getApp();
    final Object other$APP = other.getApp();
    if (this$APP == null ? other$APP != null : !this$APP.equals(other$APP)) return false;
    final Object this$GD = this.getGd();
    final Object other$GD = other.getGd();
    if (this$GD == null ? other$GD != null : !this$GD.equals(other$GD)) return false;
    return true;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof PostRequest;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $TP = this.getTp();
    result = result * PRIME + ($TP == null ? 43 : $TP.hashCode());
    final Object $ID = this.getId();
    result = result * PRIME + ($ID == null ? 43 : $ID.hashCode());
    result = result * PRIME + this.getDf();
    final Object $VER = this.getVer();
    result = result * PRIME + ($VER == null ? 43 : $VER.hashCode());
    final long $LAT = this.getLat();
    result = result * PRIME + (int) ($LAT >>> 32 ^ $LAT);
    final long $LON = this.getLon();
    result = result * PRIME + (int) ($LON >>> 32 ^ $LON);
    final Object $APP = this.getApp();
    result = result * PRIME + ($APP == null ? 43 : $APP.hashCode());
    final Object $GD = this.getGd();
    result = result * PRIME + ($GD == null ? 43 : $GD.hashCode());
    return result;
  }

  public String toString() {
    return parameter
        + "TP="
        + this.getTp()
        + "&ID="
        + this.getId()
        + "&DF="
        + this.getDf()
        + "&VER="
        + this.getVer()
        + "&LAT="
        + this.getLat()
        + "&LON="
        + this.getLon()
        + "&APP="
        + this.getApp()
        + "&GD="
        + this.getGd();
  }
}
