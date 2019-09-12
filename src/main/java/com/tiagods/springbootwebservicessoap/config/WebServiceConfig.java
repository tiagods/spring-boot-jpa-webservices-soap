package com.tiagods.springbootwebservicessoap.config;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import sun.print.resources.serviceui;

@Configuration
@EnableWs//habilitar o webservice
public class WebServiceConfig extends WsConfigurerAdapter{
	
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet messageDispatcher = new MessageDispatcherServlet();
		messageDispatcher.setApplicationContext(context);
		messageDispatcher.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<MessageDispatcherServlet>(messageDispatcher,"/ws/*");
	}
	
	@Bean
	public XsdSchema customerSchema() {
		return new SimpleXsdSchema(new ClassPathResource("customer-information.xsd"));
	}
	
	@Bean(name="customers")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("CustomerPort");
		definition.setTargetNamespace("http://tiagods.com/customer-information");
		definition.setLocationUri("/ws");
		definition.setSchema(schema);
		return definition;
	}
	
	@Bean
	public XwsSecurityInterceptor swsSecurityInterceptor() {
		XwsSecurityInterceptor xwsSecurityInterceptor = new XwsSecurityInterceptor();
		xwsSecurityInterceptor.setCallbackHandler(simplePasswordValidationCallbackHandler());
		xwsSecurityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return xwsSecurityInterceptor;
	}
	
	@Bean
	public SimplePasswordValidationCallbackHandler simplePasswordValidationCallbackHandler() {
		SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
		callbackHandler.setUsersMap(Collections.singletonMap("Tiago", "123"));
		return callbackHandler;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors){
		interceptors.add(swsSecurityInterceptor());
	}
}
