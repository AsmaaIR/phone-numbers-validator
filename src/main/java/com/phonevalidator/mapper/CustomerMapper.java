package com.phonevalidator.mapper;

import com.phonevalidator.data.entities.Customer;
import com.phonevalidator.data.models.CustomerModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends BaseMapper<Customer, CustomerModel> {

}
