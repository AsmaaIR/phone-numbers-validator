package com.phonevalidator.services;

import com.phonevalidator.data.enums.PhoneNumberStateEnum;

public interface CountryService {

    PhoneNumberStateEnum isCountryPhoneValid(String phoneNumber);

    String getCountryByCode(String phoneNumber);

    String getCodeByCountry(String country);

    boolean validateNumberWithRegex(String phoneNumber, String regex);

}
