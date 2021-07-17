package com.phonevalidator.services.impl;

import com.phonevalidator.data.enums.CountryEnum;
import com.phonevalidator.data.enums.PhoneNumberStateEnum;
import com.phonevalidator.data.models.CountryModel;
import com.phonevalidator.services.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CountryServiceImpl implements CountryService {

    @Override
    public PhoneNumberStateEnum isCountryPhoneValid(String phoneNumber) {

        List<Function<String, Boolean>> validations = Arrays.asList(
                phone -> isPhoneNumberOrPhoneCodeValid(phoneNumber),
                phone -> isPhoneRegexValid(phoneNumber)
        );
        for (Function<String, Boolean> validation : validations) {
            if (!validation.apply(phoneNumber)) {
                return PhoneNumberStateEnum.INVALID;
            }
        }
        return PhoneNumberStateEnum.VALID;
    }

    @Override
    public String getCountryByCode(String phoneNumber) {
        Map<String, CountryModel> countryModelsMap = getCodeCountryMap();
        return isPhoneNumberOrPhoneCodeValid(phoneNumber) ? getCountryName(phoneNumber, countryModelsMap) : "";
    }

    @Override
    public String getCodeByCountry(String country) {
        Map<String, CountryModel> countryModelsMap = getCountryNameCountryMap();
        return StringUtils.isBlank(country) ? "" : getCountryCode(country, countryModelsMap);
    }


    @Override
    public boolean validateNumberWithRegex(String phoneNumber, String regex) {
        return Pattern.matches(regex, phoneNumber);
    }

    private String extractCountryCodeFromPhone(String phoneNumber) {
        return StringUtils.substringBetween(phoneNumber, "(", ")");
    }

    private boolean isPhoneNumberOrPhoneCodeValid(String phoneNumber) {
        return StringUtils.isNoneBlank(phoneNumber) && StringUtils.isNoneBlank(extractCountryCodeFromPhone(phoneNumber));
    }

    private String getCountryName(String phoneNumber, Map<String, CountryModel> countryModelsMap) {
        String code = extractCountryCodeFromPhone(phoneNumber);
        return countryModelsMap.get(code) == null ? "" : countryModelsMap.get(code).getCountryName();
    }

    private String getCountryCode(String country, Map<String, CountryModel> countryModelsMap) {
        return (countryModelsMap.get(country) == null) ? "" : countryModelsMap.get(country).getCountryCode();
    }

    private List<CountryModel> initAndGetCountriesInfo() {
        List<CountryModel> countryModels = new ArrayList<>();

        countryModels.add(CountryModel.builder().countryName(CountryEnum.CAMEROON.getCountryName()).countryCode(CountryEnum.CAMEROON.getCode()).regex(CountryEnum.CAMEROON.getRegex()).build());
        countryModels.add(CountryModel.builder().countryName(CountryEnum.ETHIOPIA.getCountryName()).countryCode(CountryEnum.ETHIOPIA.getCode()).regex(CountryEnum.ETHIOPIA.getRegex()).build());
        countryModels.add(CountryModel.builder().countryName(CountryEnum.MOROCCO.getCountryName()).countryCode(CountryEnum.MOROCCO.getCode()).regex(CountryEnum.MOROCCO.getRegex()).build());
        countryModels.add(CountryModel.builder().countryName(CountryEnum.MOZAMBIQUE.getCountryName()).countryCode(CountryEnum.MOZAMBIQUE.getCode()).regex(CountryEnum.MOZAMBIQUE.getRegex()).build());
        countryModels.add(CountryModel.builder().countryName(CountryEnum.UGANDA.getCountryName()).countryCode(CountryEnum.UGANDA.getCode()).regex(CountryEnum.UGANDA.getRegex()).build());

        return countryModels;
    }

    private Map<String, CountryModel> getCodeCountryMap() {
        return initAndGetCountriesInfo().stream()
                .collect(Collectors.toMap(CountryModel::getCountryCode, countryModel -> countryModel));
    }

    private Map<String, CountryModel> getCountryNameCountryMap() {
        return initAndGetCountriesInfo().stream()
                .collect(Collectors.toMap(CountryModel::getCountryName, countryModel -> countryModel));
    }

    private boolean isPhoneRegexValid(String phoneNumber) {
        Map<String, CountryModel> countryModelsMap = getCodeCountryMap();
        boolean valid = validateNumberWithRegex
                (phoneNumber, countryModelsMap.get(extractCountryCodeFromPhone(phoneNumber)).getRegex());
        return valid;
    }

}
