package com.phonevalidator.data.models;

import com.phonevalidator.data.enums.PhoneNumberStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteriaModel {

    private List<String> countries;
    private PhoneNumberStateEnum state;
}
