package monevent.common.process.matching;

public class ExpectedMatchChecker implements IMatchingChecker {
    private final long expectedMatch;

    public ExpectedMatchChecker(long expectedMatch) {
        this.expectedMatch = expectedMatch;
    }

    public long getExpectedMatch() {
        return expectedMatch;
    }

    @Override
    public boolean isComplete(MatchingResult result) {
        if (result != null) {
            return (result.getMatch() == getExpectedMatch());
        }
        return false;
    }
}
