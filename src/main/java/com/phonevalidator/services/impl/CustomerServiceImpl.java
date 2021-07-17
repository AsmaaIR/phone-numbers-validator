package com.phonevalidator.services.impl;

import com.phonevalidator.data.entities.Customer;
import com.phonevalidator.data.enums.PhoneNumberStateEnum;
import com.phonevalidator.data.models.CustomerModel;
import com.phonevalidator.data.models.SearchCriteriaModel;
import com.phonevalidator.mapper.CustomerMapper;
import com.phonevalidator.repostitories.CustomerRepository;
import com.phonevalidator.services.CountryService;
import com.phonevalidator.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private CountryService countryService;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                           CustomerMapper customerMapper,
                           CountryService countryService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.countryService = countryService;
    }

    @Override
    public List<CustomerModel> findAll(SearchCriteriaModel searchCriteriaModel) {
        List<Customer> customers = customerRepository
                .findAll(getCountriesFilterSpecification(searchCriteriaModel));

        return getCustomerModels(customers).stream()
                .filter(isStateValid(searchCriteriaModel.getState()))
                .collect(Collectors.toList());
    }

    public static java.util.function.Predicate<CustomerModel> isStateValid(PhoneNumberStateEnum phoneNumberState) {
        return customerModel -> phoneNumberState == null || customerModel.getPhoneNumberState().equals(phoneNumberState);
    }

    private Specification<Customer> getCountriesFilterSpecification(SearchCriteriaModel searchCriteriaModel) {
        return countriesFilter(Optional.ofNullable(getCountries(searchCriteriaModel))
                        .map(Collection::stream).orElseGet(Stream::empty)
                        .map(country -> countryService.getCodeByCountry(country))
                        .collect(Collectors.toList()));
    }

    private List<String> getCountries(SearchCriteriaModel searchCriteriaModel) {
        return searchCriteriaModel == null ? null :searchCriteriaModel.getCountries();
    }

    public static Specification<Customer> countriesFilter(List<String> customerPhones) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            if (CollectionUtils.isEmpty(customerPhones)) {
                return criteriaBuilder.conjunction();
            } else {

                List<Predicate> predicates = new ArrayList<>();
                customerPhones.forEach(customerPhone -> {
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("phone")),
                            "(" + customerPhone.toLowerCase() + "%"));
                });
                return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            }
        };
    }

    private List<CustomerModel> getCustomerModels(List<Customer> customers) {
        List<CustomerModel> customerModels = customerMapper.toModels(customers);
        customerModels.stream()
                .map(customerModel -> {
                    customerModel.setPhoneNumberState(countryService.isCountryPhoneValid(customerModel.getPhone()));
                    return customerModel;
                })
                .map(customerModel -> {
                    customerModel.setCountry(countryService.getCountryByCode(customerModel.getPhone()));
                    return customerModel;
                }).collect(Collectors.toList());
        return customerModels;
    }

}
