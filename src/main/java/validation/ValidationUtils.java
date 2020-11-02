package validation;

public class ValidationUtils {

   public static void checkGreaterEqualsZero(String errorMessage,int value){
        if(value<=0){
        throw new IllegalArgumentException(errorMessage + value);
    }
}
}
