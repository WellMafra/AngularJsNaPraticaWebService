package org.example.salesserver.repository;

import java.util.List;

import org.example.salesserver.model.Customer;
import org.example.salesserver.model.QCustomer;

public class CustomerRepository extends AbstractRepository<Customer, String>{
	
	public Customer findById(String id) {
		return findById(Customer.class, id);		
	}
	
	public List<Customer> listAll(Integer offset, Integer limit) {
		return listAll(QCustomer.customer, offset, limit);
	}

}