package com.tiagods.springbootwebservicessoap.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.tiagods.customer_information.CustomerDetail;
import com.tiagods.customer_information.DeleteCustomerRequest;
import com.tiagods.customer_information.DeleteCustomerResponse;
import com.tiagods.customer_information.GetAllCustomerDetailRequest;
import com.tiagods.customer_information.GetAllCustomerDetailResponse;
import com.tiagods.customer_information.GetCustomerDetailRequest;
import com.tiagods.customer_information.GetCustomerDetailResponse;
import com.tiagods.springbootwebservicessoap.bean.Customer;
import com.tiagods.springbootwebservicessoap.excepion.CustomerNotFoundException;
import com.tiagods.springbootwebservicessoap.helper.Status;
import com.tiagods.springbootwebservicessoap.service.CustomerDetailService;

@Endpoint
public class CustomerDetailEndPoint {

	@Autowired
	private CustomerDetailService customerService;
	
	@PayloadRoot(namespace="http://tiagods.com/customer-information",localPart="GetCustomerDetailRequest")
	@ResponsePayload
	public GetCustomerDetailResponse processRequest(@RequestPayload GetCustomerDetailRequest req) {
		Customer c = customerService.findById(req.getId());
		GetCustomerDetailResponse resp = new GetCustomerDetailResponse();
		resp.setCustomerDetail(convertCustomer(c));
		return resp;
	}
	
	@PayloadRoot(namespace="http://tiagods.com/customer-information",localPart="GetAllCustomerDetailRequest")
	@ResponsePayload
	public GetAllCustomerDetailResponse getAllRequest(@RequestPayload GetAllCustomerDetailRequest req) {
		List<Customer> list = customerService.getAll();
		GetAllCustomerDetailResponse resp = new GetAllCustomerDetailResponse();
		list.forEach(c-> resp.getCustomerDetail().add(convertCustomer(c)));
		return resp;
	}
	
	@PayloadRoot(namespace="http://tiagods.com/customer-information",localPart="DeleteCustomerRequest")
	@ResponsePayload
	public DeleteCustomerResponse deleteRequest(@RequestPayload DeleteCustomerRequest req) {
		DeleteCustomerResponse res = new DeleteCustomerResponse();
		Status status = customerService.deleteById(req.getId());
		res.setStatus(convertStatusSoap(status));
		return res;
	}
	
	private com.tiagods.customer_information.Status convertStatusSoap(Status statusService){
		if(statusService==Status.ERROR) return com.tiagods.customer_information.Status.ERROR;
		else return com.tiagods.customer_information.Status.SUCCESS;
	}
	
	private CustomerDetail convertCustomer(Customer c) {
		CustomerDetail customer = new CustomerDetail();
		customer.setId(c.getId());
		customer.setNome(c.getNome());
		customer.setPhone(c.getPhone());
		customer.setEmail(c.getEmail());
		return customer;
	}
}
