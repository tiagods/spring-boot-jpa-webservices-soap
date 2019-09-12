package com.tiagods.springbootwebservicessoap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagods.springbootwebservicessoap.bean.Customer;
import com.tiagods.springbootwebservicessoap.excepion.CustomerNotFoundException;
import com.tiagods.springbootwebservicessoap.helper.Status;
import com.tiagods.springbootwebservicessoap.repository.CustomersRepository;

@Service
public class CustomerDetailService {
	
	@Autowired
	private CustomersRepository repository;
	
	@PostConstruct
	private void baseGenerator(){	
		List<Customer> list = new ArrayList<>();
		list.add(new Customer(1,"Bob","155555555","mail@mail.com"));
		list.add(new Customer(2,"Joao","1133336666","joao@joao.com"));
		list.add(new Customer(3,"Marcos","1122224567","email@email.com"));
		list.add(new Customer(4,"Aurelio","1199999654","aurelio@email.com"));
		list.add(new Customer(5,"Silvio","8211110000","silvio@silvio.com"));
		repository.saveAll(list);
	}
	public List<Customer> getAll(){
		return repository.findAll();
	}
	public Customer findById(int id) {
		Optional<Customer> result = buscar(id);
		if(result.isPresent()) return result.get();
		else throw new CustomerNotFoundException("Customer Not Found id: "+id);
	}
	
	public Status deleteById(int id) throws CustomerNotFoundException{
		Optional<Customer> result = buscar(id);
		if(result.isPresent()) {
			repository.delete(result.get());
			return Status.SUCCESS;
		} 
		else 
			throw new CustomerNotFoundException("Customer Not Found id: "+id);
	}
	private Optional<Customer> buscar(int id) {
		return repository.findById(id);
	}
}
