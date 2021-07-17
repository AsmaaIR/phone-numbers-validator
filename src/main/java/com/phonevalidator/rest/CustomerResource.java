package com.phonevalidator.rest;

import com.phonevalidator.data.models.CustomerModel;
import com.phonevalidator.data.models.SearchCriteriaModel;
import com.phonevalidator.services.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customers")
public class CustomerResource {

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "this api returns customers numbers with state and country")
    @GetMapping
    public ResponseEntity<List<CustomerModel>> getAllNumbers(SearchCriteriaModel searchCriteriaModel) {
        return ResponseEntity.ok(customerService.findAll(searchCriteriaModel));
    }
}
