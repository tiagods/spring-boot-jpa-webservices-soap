package com.tiagods.client;

import com.tiagods.*;

public class ClientTest {
    public static void main(String[] args){
        CustomerPortService service = new CustomerPortService();

        HeaderHandlerResolver handlerResolver = new HeaderHandlerResolver();
        service.setHandlerResolver(handlerResolver);

        CustomerPort port = service.getCustomerPortSoap11();

        GetCustomerDetailRequest request = new GetCustomerDetailRequest();
        request.setId(1);

        GetCustomerDetailResponse response = port.getCustomerDetail(request);
        if(response == null) System.out.println(response.getCustomerDetail());
        System.out.println("Id >>>> "+response.getCustomerDetail());
    }
}
