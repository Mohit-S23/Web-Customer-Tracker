package com.mohit.spring.dao;

import java.util.List;

import com.mohit.spring.entity.Customer;

public interface CustomerDAO {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);
}
