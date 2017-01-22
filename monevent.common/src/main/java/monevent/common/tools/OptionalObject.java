package monevent.common.tools;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Stephane on 19/11/2015.
 */
public class OptionalObject {


    public static boolean booleanValue(Object item, boolean defaultValue) {
        Optional<Object> optional = Optional.ofNullable(item);
        return optional.isPresent() ? Boolean.valueOf(optional.get().toString()) : defaultValue;
    }

    public static long longValue(Object item, long defaultValue) {
        Optional<Object> optional = Optional.ofNullable(item);
        return optional.isPresent() ? Long.valueOf(optional.get().toString()) : defaultValue;
    }

    public static int integerValue(Object item, int defaultValue) {
        Optional<Object> optional = Optional.ofNullable(item);
        return optional.isPresent() ? Integer.valueOf(optional.get().toString()) : defaultValue;
    }

    public static String stringValue(Object item, String defaultValue) {
        Optional<Object> optional = Optional.ofNullable(item);
        return optional.isPresent() ? String.valueOf(optional.get().toString()) : defaultValue;
    }

    public static <T extends Enum<T>> T enumValue(Object item, Class<T> enumType, T defaultValue) {
        Optional<Object> opt = Optional.ofNullable(item);
        return opt.isPresent() ? Arrays.stream(enumType.getEnumConstants()) //
                .filter(e -> e.name().equals(opt.get().toString())).findFirst().orElse(defaultValue) : defaultValue;

    }

}
