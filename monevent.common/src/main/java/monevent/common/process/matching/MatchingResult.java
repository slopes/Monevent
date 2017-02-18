package monevent.common.process.matching;

import monevent.common.model.Entity;

/**
 * Created by slopes on 01/02/17.
 */
public class MatchingResult extends Entity {
    protected static String match = "match";

    public MatchingResult(String type) {
        super(type);
        setValue(MatchingResult.match, 0);
    }

    public long getMatch() {
        return getValueAsLong(MatchingResult.match);
    }

    public void setMatch(long match) {
        setValue(MatchingResult.match, match);
    }

    public void addMatch() {
        long match = getValueAsLong(MatchingResult.match);
        setMatch(match + 1);
    }
}
