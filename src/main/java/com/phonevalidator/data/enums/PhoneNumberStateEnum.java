package com.phonevalidator.data.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PhoneNumberStateEnum {

    VALID(0), INVALID(1);

    private final Integer value;
}
