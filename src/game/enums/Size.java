package game.enums;

public enum Size {
    SMALL,
    MEDIUM,
    LARGE;
    public Size getSmaller() {
        return (this== Size.SMALL || this == Size.MEDIUM) ? Size.SMALL :Size. MEDIUM;
    }
}