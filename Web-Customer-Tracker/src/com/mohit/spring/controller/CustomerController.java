package com.mohit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mohit.spring.entity.Customer;
import com.mohit.spring.service.CustomerService;
import com.mohit.spring.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model theModel, @RequestParam(required = false) String sort) {
		
		// get customers from the service
		List<Customer> theCustomers = null;
		
		if(sort != null) {
			int theSortField = Integer.parseInt(sort);
			theCustomers = customerService.getCustomers(theSortField);
		}
		else
		{
			// by deafult sorting on First Name
			theCustomers = customerService.getCustomers(SortUtils.FIRST_NAME);
		}
		
		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		
		customerService.saveCustomer(theCustomer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		
		Customer theCustomer = customerService.getCustomer(theId);
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		
		customerService.deleteCustomer(theId);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel) {
		
		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
		
		theModel.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
}
