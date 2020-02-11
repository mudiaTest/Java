package validate;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Negative;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;;

public class ValidatedObj {

  @Max(value = 2, groups = ValGroup1.class)
  public Integer i;
  @Email(groups = { ValGroup1.class, ValGroup2.class })
  String s;

//  @NotEmpty// � validates that the property is not null or empty; can be applied to String, Collection, Map or Array values
//  @NotBlank// � can be applied only to text values and validated that the property is not null or whitespace
//  @Positive 
////  @PositiveOrZero// � apply to numeric values and validate that they are strictly positive, or positive including 0
////  @Negative//
////  @NegativeOrZero// � apply to numeric values and validate that they are strictly negative, or negative including 0
  @Past
////  //@PastOrPresent// � validate that a date value is in the past or the past including the present; can be applied to date types including those added in Java 8
////  //@Future
////  //@FutureOrPresent 
  public Integer i6;
}
