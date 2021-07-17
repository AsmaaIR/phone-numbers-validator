package com.phonevalidator.data.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryModel {

    private String countryName;
    private String countryCode;
    private String regex;
}
