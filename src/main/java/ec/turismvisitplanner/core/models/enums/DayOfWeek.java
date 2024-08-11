package ec.turismvisitplanner.core.models.enums;

public enum DayOfWeek {

    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    private final int dayNumber;

    DayOfWeek(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public boolean isWeekend() {
        return this == SATURDAY || this == SUNDAY;
    }
}
