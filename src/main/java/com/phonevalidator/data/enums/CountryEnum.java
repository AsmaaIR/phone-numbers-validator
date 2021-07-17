package com.phonevalidator.data.enums;

public enum CountryEnum {

    CAMEROON("Cameroon", "237", "\\(237\\)\\ ?[2368]\\d{7,8}$"),
    ETHIOPIA("Ethiopia", "251", "\\(251\\)\\ ?[1-59]\\d{8}$"),
    MOROCCO("Morocco", "212", "\\(212\\)\\ ?[5-9]\\d{8}$"),
    MOZAMBIQUE("Mozambique", "258", "\\(258\\)\\ ?[28]\\d{7,8}$"),
    UGANDA("Uganda", "256", "\\(256\\)\\ ?\\d{9}$"),;

    private final String countryName;
    private final String code;
    private final String regex;

    CountryEnum(String countryName, String code, String regex) {
        this.countryName = countryName;
        this.code = code;
        this.regex = regex;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCode() {
        return code;
    }

    public String getRegex() {
        return regex;
    }
}
