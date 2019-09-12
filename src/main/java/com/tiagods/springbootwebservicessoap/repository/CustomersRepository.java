package com.tiagods.springbootwebservicessoap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiagods.springbootwebservicessoap.bean.Customer;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Integer>{

}
