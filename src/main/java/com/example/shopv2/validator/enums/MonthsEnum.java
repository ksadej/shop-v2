package com.example.shopv2.validator.enums;

public enum MonthsEnum {
    JANUARY("01", "31"),
    FEBRUARY("02", "28"),
    MARCH("03", "31"),
    APRIL("04", "30"),
    MAY("05", "31"),
    JUNE("06", "30"),
    JULY("07", "31"),
    AUGUST("08", "31"),
    SEPTEMBER("09", "30"),
    NOVEMBER("10", "31"),
    OCTOBER("11", "30"),
    DECEMBER("12", "31");

    private final String monthNumber;
    private final String maxDays;

    MonthsEnum(String monthNumber, String maxDays) {
        this.monthNumber = monthNumber;
        this.maxDays = maxDays;
    }

    public String getFirstDayForYear(String year) {
        return year + "-" + this.monthNumber + "-01";
    }

    public String getLastDayForYear(String year) {
        return year + "-" + this.monthNumber + "-" + this.maxDays;
    }
}
