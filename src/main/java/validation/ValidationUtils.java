package validation;

import java.util.Properties;

public class ValidationUtils {

  public static void checkGreaterEqualsZero(String errorMessage, int value) {
    if (value <= 0) {
      throw new IllegalArgumentException(errorMessage + value);
    }
  }

  public static void checkIfEmpty(String errorMessage, Properties properties) {
    if (properties.isEmpty()) {
      throw new IllegalArgumentException(errorMessage);
    }
  }
}
