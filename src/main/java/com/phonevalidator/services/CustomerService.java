package com.phonevalidator.services;

import com.phonevalidator.data.enums.PhoneNumberStateEnum;
import com.phonevalidator.data.models.CustomerModel;
import com.phonevalidator.data.models.SearchCriteriaModel;
import com.phonevalidator.services.impl.CustomerServiceImpl;

import java.util.List;

public interface CustomerService {

    List<CustomerModel> findAll(SearchCriteriaModel searchCriteriaModel);

}
