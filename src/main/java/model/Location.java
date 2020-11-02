package model;

import lombok.Data;

@Data
public class Location {
  private double latitude, longitude;
  public Location(){}
  public Location(double latitude,double longitude){
    this.latitude=latitude;
    this.longitude=longitude;
  }
}
