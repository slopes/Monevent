package monevent.common.model.alert;

/**
 * Created by Stephane on 19/11/2015.
 */
public enum AlertPriority {
    Undefined(-1),
    Fatal(5),
    Critical(4),
    Medium(3),
    Low(2),
    Planned(1),
    Info(0);

    private final int value;

    private AlertPriority(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

