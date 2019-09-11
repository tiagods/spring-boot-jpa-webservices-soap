package com.tiagods.springbootwebservicessoap.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.tiagods.customer_information.CustomerDetail;
import com.tiagods.customer_information.GetCustomerDetailRequest;
import com.tiagods.customer_information.GetCustomerDetailResponse;

@Endpoint
public class CustomerDetailEndPoint {

	@PayloadRoot(namespace="http://tiagods.com/customer-information",localPart="GetCustomerDetailRequest")
	@ResponsePayload
	public GetCustomerDetailResponse process(@RequestPayload GetCustomerDetailRequest req) {
		GetCustomerDetailResponse resp = new GetCustomerDetailResponse();
		CustomerDetail customer = new CustomerDetail();
		customer.setId(req.getId());
		customer.setNome("Bob");
		customer.setPhone("155555555");
		customer.setEmail("mail@mail.com");
		resp.setCustomerDetail(customer);
		return resp;
	}
}
