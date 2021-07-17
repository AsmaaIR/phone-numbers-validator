package com.phonevalidator.servives;

import com.phonevalidator.data.enums.CountryEnum;
import com.phonevalidator.data.enums.PhoneNumberStateEnum;
import com.phonevalidator.services.CountryService;
import com.phonevalidator.services.impl.CountryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class CountryServiceTest {

    private CountryService countryService;

    @BeforeEach
    public void initObj() {
        MockitoAnnotations.initMocks(this);
        countryService = new CountryServiceImpl();
    }

    @Test
    public void shouldReturnInvalidForInputCountryPhone() {
        PhoneNumberStateEnum phoneNumberState = countryService.isCountryPhoneValid("213422");
        Assertions.assertEquals(PhoneNumberStateEnum.INVALID, phoneNumberState);
    }


    @Test
    public void shouldReturnMorroccoForInputCountryPhone() {
        String country = countryService.getCountryByCode("(212) 698054317");
        Assertions.assertEquals(CountryEnum.MOROCCO.getCountryName(), country);
    }

    @Test
    public void shouldReturnEmptyForInputCountryPhone() {
        String country = countryService.getCountryByCode("(235) 698054317");
        Assertions.assertEquals("", country);
    }

    @Test
    public void shouldReturnValidCountryCodeForInputCountryMorocco() {
        String code = countryService.getCodeByCountry(CountryEnum.MOROCCO.getCountryName());
        Assertions.assertEquals(CountryEnum.MOROCCO.getCode(), code);
    }

    @Test
    public void shouldReturnEmptyCountryCodeForInputCountryMorocco() {
        String code = countryService.getCodeByCountry("Italy");
        Assertions.assertEquals("", code);
    }
}
