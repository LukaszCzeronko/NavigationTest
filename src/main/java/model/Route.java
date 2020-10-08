package model;

import lombok.Data;

import java.util.List;

@Data
public class Route {
  private int id;
  private double length;
  private String units;
  private List<Location> location;
}
