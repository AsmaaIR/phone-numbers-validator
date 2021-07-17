package com.phonevalidator.data.models;

import com.phonevalidator.data.enums.PhoneNumberStateEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerModel {

    private String name;
    private String phone;
    private String country;
    private PhoneNumberStateEnum phoneNumberState;
}
