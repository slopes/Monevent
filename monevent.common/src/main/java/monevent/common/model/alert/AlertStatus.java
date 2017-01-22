package monevent.common.model.alert;

/**
 * Created by Stephane on 19/11/2015.
 */
public enum AlertStatus {
    Undefined(-1),
    Open(0),
    Closed(1),
    Cancelled(2);

    private final int value;

    private AlertStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
