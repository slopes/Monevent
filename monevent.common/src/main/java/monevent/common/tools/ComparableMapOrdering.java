package monevent.common.tools;

import com.google.common.collect.Ordering;

/**
 * Created by Stephane on 18/11/2015.
 */
public class ComparableMapOrdering extends Ordering<ComparableMap> {
    private final String field;

    public ComparableMapOrdering(String field) {
        this.field = field;
    }

    @Override
    public int compare(ComparableMap map, ComparableMap otherMap) {
        if (field == null) return 0;
        if (map == null && otherMap == null) return 0;
        if (map == null) return 1;
        if (otherMap == null) return -1;
        Comparable value = (map.containsKey(field)) ? (Comparable) map.get(field) : null;
        Comparable otherValue = (otherMap.containsKey(field)) ? (Comparable) otherMap.get(field) : null;
        if (value == null && otherValue == null) return 0;
        if (value == null) return 1;
        if (otherValue == null) return -1;
        return value.compareTo(otherValue);
    }
}
