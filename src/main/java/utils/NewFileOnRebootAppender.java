package utils;

import org.apache.log4j.FileAppender;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewFileOnRebootAppender extends FileAppender {

  public NewFileOnRebootAppender() {}

  @Override
  public void setFile(String file) {
    super.setFile(prependDate(file));
  }

  private static String prependDate(String filename) {
    long time = System.currentTimeMillis();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    Date date = new Date(time);
    return "target//" + format.format(date) + "_" + filename;
  }
}
