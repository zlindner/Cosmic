package zachy.cosmic.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Needs to be implemented on a public static {@link IAPI} field.
 */
@Target(ElementType.FIELD)
public @interface IInjector {

}
