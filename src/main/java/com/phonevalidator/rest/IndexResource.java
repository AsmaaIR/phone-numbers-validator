package com.phonevalidator.rest;

import com.phonevalidator.data.models.CustomerModel;
import com.phonevalidator.data.models.SearchCriteriaModel;
import com.phonevalidator.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexResource {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/")
    public String index(Model model, SearchCriteriaModel searchCriteriaModel) {
        List<CustomerModel> customerModels = customerService.findAll(searchCriteriaModel);
        model.addAttribute("customersList", customerModels);
        return "index";
    }
}