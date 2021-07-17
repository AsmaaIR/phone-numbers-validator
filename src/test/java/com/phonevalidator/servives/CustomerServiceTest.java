package com.phonevalidator.servives;

import com.phonevalidator.data.entities.Customer;
import com.phonevalidator.data.enums.PhoneNumberStateEnum;
import com.phonevalidator.data.models.CustomerModel;
import com.phonevalidator.data.models.SearchCriteriaModel;
import com.phonevalidator.mapper.CustomerMapper;
import com.phonevalidator.mapper.CustomerMapperImpl;
import com.phonevalidator.repostitories.CustomerRepository;
import com.phonevalidator.services.CountryService;
import com.phonevalidator.services.CustomerService;
import com.phonevalidator.services.impl.CountryServiceImpl;
import com.phonevalidator.services.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    CustomerService customerService;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    CustomerMapper customerMapper;
    @Mock
    CountryService countryService;

    private List<Customer> customers;
    private List<CustomerModel> customerModels;

    @BeforeEach
    public void initObj() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, customerMapper,countryService);

        customers = new ArrayList<Customer>() {{
            add(Customer.builder().name("asmaa").phone("(212) 6007989253").build());
            add(Customer.builder().name("mohammed").phone("(258) 847602609").build());
            add(Customer.builder().name("ibrahim").phone("(212) 698054317").build());
        }};

        customerModels = new ArrayList<CustomerModel>() {{
            add(CustomerModel.builder().name("asmaa").phone("(212) 6007989253").build());
            add(CustomerModel.builder().name("mohammed").phone("(258) 847602609").build());
            add(CustomerModel.builder().name("ibrahim").phone("(212) 698054317").build());
        }};

        mockDependenciesActions();
    }

    private void mockDependenciesActions() {
        when(customerRepository.findAll(Mockito.any(Specification.class))).thenReturn(customers);
        when(customerMapper.toModels(customers)).thenReturn(customerModels);
        when(countryService.isCountryPhoneValid(customerModels.get(0).getPhone())).thenReturn(PhoneNumberStateEnum.INVALID);
        when(countryService.isCountryPhoneValid(customerModels.get(1).getPhone())).thenReturn(PhoneNumberStateEnum.VALID);
        when(countryService.isCountryPhoneValid(customerModels.get(2).getPhone())).thenReturn(PhoneNumberStateEnum.INVALID);
        when(countryService.getCountryByCode(customerModels.get(0).getPhone())).thenReturn("Morocco");
        when(countryService.getCountryByCode(customerModels.get(1).getPhone())).thenReturn("Mozambique");
        when(countryService.getCountryByCode(customerModels.get(2).getPhone())).thenReturn("Morocco");
    }

    @Test
    public void shouldFilterCustomersAndReturnOnlyValidNumbersState() {

        List<CustomerModel> result = customerService.findAll(SearchCriteriaModel.builder().state(PhoneNumberStateEnum.VALID).build());

        result.forEach((customerModel) ->
                Assertions.assertEquals(
                        PhoneNumberStateEnum.VALID,
                        customerModel.getPhoneNumberState()));
    }

    @Test
    public void shouldFilterCustomersAndReturnOnlyMorroccoAndMozambique() {

        List<CustomerModel> result = customerService.findAll(SearchCriteriaModel.builder().state(PhoneNumberStateEnum.VALID).build());

        result.forEach((customerModel) ->
                Assertions.assertEquals(
                        PhoneNumberStateEnum.VALID,
                        customerModel.getPhoneNumberState()));
    }

}
